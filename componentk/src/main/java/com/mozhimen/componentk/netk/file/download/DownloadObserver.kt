package com.mozhimen.componentk.netk.file.download

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import com.mozhimen.componentk.R
import com.mozhimen.componentk.netk.file.download.cons.CDownloadConstants
import com.mozhimen.componentk.netk.file.download.cons.CErrorCode
import com.mozhimen.componentk.netk.file.download.utils.Utils.getPercent


/**
 *
 * @author by chiclaim@google.com
 */
internal class DownloadObserver(
    context: Context,
    private val downloadId: Long,
    private var downloader: Downloader
) : ContentObserver(null) {


    private val context: Context = context.applicationContext

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }


    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        DownloadExecutor.execute {
            val info = SystemDownloadManager(context).getDownloadInfo(downloadId) ?: return@execute
            handler.post {
                downloader.request.onProgressUpdate(getPercent(info.totalSize, info.downloadedSize))
                when (info.status) {
                    CDownloadConstants.STATUS_SUCCESSFUL -> {
                        context.contentResolver.unregisterContentObserver(this)
                        val uri = info.uri
                        if (uri == null) {
                            downloader.request.onFailed(
                                DownloadException(
                                    CErrorCode.ERROR_MISSING_URI,
                                    context.getString(R.string.downloader_notifier_failed_missing_uri)
                                )
                            )
                        } else {
                            downloader.request.onComplete(uri)
                        }
                    }
                    CDownloadConstants.STATUS_FAILED -> {
                        context.contentResolver.unregisterContentObserver(this)
                        downloader.request.onFailed(
                            DownloadException(
                                CErrorCode.ERROR_DM_FAILED,
                                context.getString(
                                    R.string.downloader_notifier_content_err_placeholder,
                                    info.reason ?: "-"
                                )
                            )
                        )
                    }
                }
            }
        }

    }
}