package com.mozhimen.componentk.netk.file.download

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.security.UtilKMd5
import com.mozhimen.componentk.R
import com.mozhimen.componentk.netk.file.download.cons.CDownloadConstants
import com.mozhimen.componentk.netk.file.download.cons.CDownloadParameter
import com.mozhimen.componentk.netk.file.download.cons.CErrorCode
import com.mozhimen.componentk.netk.file.download.utils.Utils.getPercent
import com.mozhimen.componentk.netk.file.download.utils.Utils.getTipFromException
import com.mozhimen.componentk.netk.file.download.utils.NotifierUtils
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 *
 * @author by chiclaim@google.com
 */
class EmbedDownloader(request: DownloadRequest) :
    Downloader(request), IUtilK {
    override val TAG: String = "EmbedDownloader>>>>>"

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }

    // 避免下载期间，创建大量的通知对象
    private var lastNotifyTime = 0L

    private fun prepareConnection(uri: URL?, currentLength: Long): HttpURLConnection {

        val url = uri ?: URL(request.url)
        val conn = url.openConnection() as HttpURLConnection

        conn.requestMethod = "GET"
        conn.connectTimeout = 10 * 1000
        // Defeat transparent gzip compression, since it doesn't allow us to
        // easily resume partial downloads.
        conn.setRequestProperty("Accept-Encoding", "identity")
        // Only splice in user agent when not already defined
        if (conn.getRequestProperty("User-Agent") == null) {
            conn.addRequestProperty("User-Agent", "AndroidDownloader/1.0")
        }

        val resuming = !request.ignoreLocal && currentLength > 0
        if (resuming) {
            // continue last download
            conn.setRequestProperty("Range", "bytes=$currentLength-")
        }
        return conn
    }

    private fun prepareDestinationFile(): File {
        val file = File(
            request.destinationUri?.path
                ?: throw NullPointerException("request must set destinationDir path")
        )
        if (file.isDirectory) {
            val ext: String = request.url.substringAfterLast(".", "").run {
                if (length > 10) "" else ".${this}"
            }
            return File(file, "${UtilKMd5.str2strMd5(request.url)}$ext")
        }
        return file
    }

    @SuppressLint("WrongConstant")
    private fun fillRequestFromDB(dbRecord: DownloadRecord) {
        dbRecord.notificationTitle?.let {
            request.setNotificationTitle(it)
        }
        dbRecord.notificationContent?.let {
            request.setNotificationContent(it)
        }
        dbRecord.destinationUri?.let {
            request.setDestinationUri(Uri.parse(it))
        }
        request.setIgnoreLocal(dbRecord.ignoreLocal)
        request.setNeedInstall(dbRecord.needInstall)
        request.setNotificationVisibility(dbRecord.notificationVisibility)
    }

    private fun prepareRecord(destinationFile: File): DownloadRecord {
        var inputRecord = DownloadRecord(
            url = request.url,
            fileName = destinationFile.name,
            destinationUri = destinationFile.absolutePath,
            ignoreLocal = request.ignoreLocal,
            needInstall = request.needInstall,
            notificationTitle = request.notificationTitle.toString(),
            notificationContent = request.notificationContent.toString(),
            notificationVisibility = request.notificationVisibility,
            status = CDownloadConstants.STATUS_RUNNING
        )
        inputRecord = inputRecord.queryByUrl(request.context).firstOrNull().run {
            if (this == null) {
                val rowId = inputRecord.insert(request.context)
                if (rowId != -1L) {
                    inputRecord.queryByUrl(request.context).firstOrNull() ?: inputRecord
                } else {
                    inputRecord
                }
            } else { // 如果数据库存在记录
                // 来自通知栏点击(request 中只有 url 属性值)
                if (request.fromNotifier) {
                    fillRequestFromDB(this)
                    this.status = CDownloadConstants.STATUS_RUNNING
                    this
                }
                // 普通下载，以传递进来的 request 为准，设置 id、totalBytes 即可
                else {
                    inputRecord.id = this.id
                    inputRecord.totalBytes = this.totalBytes
                    inputRecord
                }
            }
        }
        return inputRecord
    }

    private fun checkComplete(record: DownloadRecord, currentLength: Long): Boolean {
        return !record.ignoreLocal && record.totalBytes > 0 && record.totalBytes == currentLength
    }


    private fun callPercent(percent: Int) {
        handler.post {
            request.onProgressUpdate(percent)
            if (request.notificationVisibility == CDownloadConstants.NOTIFIER_VISIBLE_NOTIFY_COMPLETED
                || request.notificationVisibility == CDownloadConstants.NOTIFIER_VISIBLE
            ) {
                NotifierUtils.showNotification(
                    request.context,
                    request.url.hashCode(),
                    request.notificationSmallIcon,
                    percent,
                    request.notificationTitle ?: "",
                    request.notificationContent,
                    CDownloadConstants.STATUS_RUNNING,
                )
            }
        }

    }

    private fun callSuccessful(record: DownloadRecord, destinationFile: File) {
        record.status = CDownloadConstants.STATUS_SUCCESSFUL
        record.update(request.context)
        handler.post {
            when (request.notificationVisibility) {
                CDownloadConstants.NOTIFIER_VISIBLE_NOTIFY_COMPLETED, CDownloadConstants.NOTIFIER_VISIBLE_NOTIFY_ONLY_COMPLETION -> {
                    NotifierUtils.showNotification(
                        request.context,
                        request.url.hashCode(),
                        request.notificationSmallIcon,
                        100,
                        request.notificationTitle ?: "",
                        request.context.getString(R.string.downloader_notifier_success_to_install),
                        CDownloadConstants.STATUS_SUCCESSFUL,
                        destinationFile
                    )
                }

                CDownloadConstants.NOTIFIER_VISIBLE -> {
                    NotifierUtils.cancelNotification(request.context, request.url.hashCode())
                }

            }
            request.onComplete(Uri.fromFile(destinationFile))
            if (record.needInstall) {
                //startInstall(request.context, destinationFile)
            }
        }
    }

    private fun callFailed(record: DownloadRecord?, e: Exception) {
        record?.let {
            it.status = CDownloadConstants.STATUS_FAILED
            it.update(request.context)
        }
        handler.post {
            if (request.notificationVisibility != CDownloadConstants.NOTIFIER_HIDDEN
            ) {
                NotifierUtils.showNotification(
                    request.context,
                    request.url.hashCode(),
                    request.notificationSmallIcon,
                    -1,
                    request.notificationTitle ?: "",
                    getTipFromException(request.context, e),
                    CDownloadConstants.STATUS_FAILED,
                    url = request.url
                )
            }
            request.onFailed(e)
        }
    }


    override fun download() {
        DownloadExecutor.execute {
            var url: URL? = null
            var conn: HttpURLConnection? = null
            var redirectCount = 0
            while (true) {
                var record: DownloadRecord? = null
                try {
                    if (redirectCount >= CDownloadParameter.EMBED_MAX_REDIRECTS) {
                        throw DownloadException(CErrorCode.ERROR_TOO_MANY_REDIRECTS, "too many redirect times")
                    }

                    val destinationFile = prepareDestinationFile().also {
                        Log.d(TAG, "download: prepareDestinationFile ${it.absolutePath}")
                    }

                    record = prepareRecord(destinationFile)


                    val currentLength =
                        if (destinationFile.exists()) destinationFile.length() else 0
                    if (checkComplete(record, currentLength)) {
                        callSuccessful(record, destinationFile)
                        break
                    }

                    conn = prepareConnection(url, currentLength)

                    // local function to reuse code
                    fun writeFile(append: Boolean = false) {
                        var wroteLength: Long = if (append) currentLength else 0
                        conn.inputStream.use { input ->
                            FileOutputStream(destinationFile, append).use { fos ->
                                val data = ByteArray(8 * 1024)
                                var count: Int
                                while (input.read(data).also { len -> count = len } != -1) {
                                    fos.write(data, 0, count)
                                    wroteLength += count
                                    fos.flush()
                                    val now = SystemClock.elapsedRealtime()
                                    val notifyInterval = now - lastNotifyTime
                                    if (notifyInterval > 700 || record.totalBytes == wroteLength) {
                                        val percent = getPercent(record.totalBytes, wroteLength)
                                        callPercent(percent)
                                        lastNotifyTime = now
                                    }
                                }
                            }
                        }
                        callSuccessful(record, destinationFile)
                    }

                    val resuming = conn.getRequestProperty("Range")?.isNotEmpty() ?: false

                    when (conn.responseCode) {
                        // 200 正常下载
                        HttpURLConnection.HTTP_OK -> {
                            if (resuming) {
                                throw DownloadException(
                                    CErrorCode.ERROR_CANNOT_RESUME,
                                    "Expected partial, but received OK"
                                )
                            }
                            record.totalBytes = conn.contentLength.toLong()
                            record.update(request.context)
                            writeFile()
                        }
                        // 206 断点续传
                        HttpURLConnection.HTTP_PARTIAL -> {
                            if (!resuming) {
                                throw DownloadException(
                                    CErrorCode.ERROR_CANNOT_RESUME,
                                    "Expected OK, but received partial"
                                )
                            }
                            writeFile(true)
                        }
                        // 301,302,303,307 308 重定向
                        HttpURLConnection.HTTP_MOVED_PERM,
                        HttpURLConnection.HTTP_MOVED_TEMP,
                        HttpURLConnection.HTTP_SEE_OTHER,
                        CDownloadParameter.EMBED_HTTP_TEMP_REDIRECT, CDownloadParameter.EMBED_HTTP_PERM_REDIRECT -> {
                            conn.disconnect()
                            val locationUrl: String =
                                conn.getHeaderField("Location")
                                    ?: throw DownloadException(
                                        CErrorCode.ERROR_MISSING_LOCATION_WHEN_REDIRECT,
                                        "missing Location in redirect"
                                    )
                            // 获取最新的地址后，重新下载
                            url = URL(url, locationUrl)
                            redirectCount++
                            continue
                        }
                        // 416 服务器无法处理所请求的数据区间
                        CDownloadParameter.EMBED_HTTP_REQUESTED_RANGE_NOT_SATISFIABLE -> {
                            conn.disconnect()
                            record.delete(request.context)
                            request.setIgnoreLocal(true)
                            continue
                        }
                        // unmatched case
                        else -> {
                            throw DownloadException(
                                CErrorCode.ERROR_UNHANDLED,
                                "Download Failed: ${conn.responseCode}",
                                conn.responseCode
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    callFailed(record, e)
                    break // break while on exception
                } finally {
                    conn?.disconnect()
                }
                break // break while on normal
            }
        }
    }
}