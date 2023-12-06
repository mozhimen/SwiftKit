package com.mozhimen.componentk.netk.app.unzip

import android.os.Environment
import android.util.Log
import androidx.annotation.WorkerThread
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.taskk.handler.TaskKHandler
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.createFolder
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.inputStream2file
import com.mozhimen.basick.utilk.java.io.inputStream2fileOfBufferedOps
import com.mozhimen.basick.utilk.kotlin.collections.containsBy
import com.mozhimen.basick.utilk.kotlin.createFolder
import com.mozhimen.basick.utilk.kotlin.deleteFolder
import com.mozhimen.basick.utilk.kotlin.getSplitExLast
import com.mozhimen.basick.utilk.kotlin.normalize
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppErrorCode
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import com.mozhimen.componentk.netk.app.download.mos.intAppErrorCode2appDownloadException
import com.mozhimen.componentk.netk.app.task.db.AppTask
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.CopyOnWriteArrayList
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * @ClassName AppZipManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 15:09
 * @Version 1.0
 */
@OptInApiInit_InApplication
internal object NetKAppUnzipManager : IUtilK {
    /**
     *  过滤在mac上压缩时自动生成的__MACOSX文件夹
     */
    private const val MAC__IGNORE = "__MACOSX/"

    //////////////////////////////////////////////////////////////////

    private val _unzippingTasks = CopyOnWriteArrayList<AppTask>()
    private val _unzippingProgressTasks = CopyOnWriteArrayList<AppTask>()

    //////////////////////////////////////////////////////////////////

    /**
     * 判断当前应用是否在解压过程中
     * @return true 表示正在解压中 false 不在解压中
     */
    @JvmStatic
    fun isUnziping(appTask: AppTask): Boolean {
        return _unzippingTasks.containsBy { it.taskId == appTask.taskId } && appTask.isTaskUnzip()
    }

    @JvmStatic
    fun hasUnziping(): Boolean {
        return _unzippingTasks.isNotEmpty()
    }

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun unzip(appTask: AppTask) {
//        if (isUnziping(appTask)) {//正在解压
//            Log.d(TAG, "unzip: the task already unziping")
//            return
//        }
        /**
         * [CNetKAppState.STATE_UNZIPING]
         */
        NetKApp.onUnziping(appTask, 100, appTask.apkFileSize, appTask.apkFileSize, 0)

        if (appTask.apkFileName.endsWith("apk") && !appTask.apkUnzipNeed) {
            onUnzipSuccess(appTask)
        }

        TaskKExecutor.execute(TAG + "unzip") {
            try {
                val strPathNameUnzip = unzipOnBack(appTask)
                Log.d(TAG, "unzip: strPathNameUnzip $strPathNameUnzip")
                if (strPathNameUnzip.isEmpty())
                    throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()
                else if (!strPathNameUnzip.endsWith("apk"))
                    throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()

                /////////////////////////////////////////////////////////

                TaskKHandler.post {
                    onUnzipSuccess(appTask.apply { apkPathName = strPathNameUnzip })
                }
            } catch (e: AppDownloadException) {
                TaskKHandler.post {
                    /**
                     * [CNetKAppState.STATE_UNZIP_FAIL]
                     */
                    NetKApp.onUnzipFail(appTask, e)
                }
            }
        }
    }

    private fun onUnzipSuccess(appTask: AppTask) {
        Log.d(TAG, "onUnzipSuccess: 解压成功 appTask $appTask")
        /**
         * [CNetKAppState.STATE_UNZIP_SUCCESS]
         */
        NetKApp.onUnzipSuccess(appTask)
    }

    private fun onUnziping(appTask: AppTask, progress: Int, currentIndex: Long, totalIndex: Long, offsetIndexPerSeconds: Long) {
        Log.v(TAG, "onUnziping: 解压进度 progress $progress")
        /**
         * [CNetKAppState.STATE_UNZIPING]
         */
        NetKApp.onUnziping(appTask, progress, currentIndex, totalIndex, offsetIndexPerSeconds)
    }

    @WorkerThread
    private fun unzipOnBack(appTask: AppTask): String {
        _unzippingTasks.add(appTask)//开始解压，添加到列表中

        val externalFilesDownloadDir = UtilKFileDir.External.getFilesDownloadsDir() ?: run {
            throw CNetKAppErrorCode.CODE_UNZIP_DIR_NULL.intAppErrorCode2appDownloadException()
        }
        val fileSource = appTask.apkPathName.strFilePath2file()
        Log.d(TAG, "unzipOnBack: fileSource ${fileSource.absolutePath}")

        val strPathNameApk = if (appTask.apkFileName.endsWith(".npk"))
            unzipNpkOnBack(fileSource, externalFilesDownloadDir.absolutePath)
        else
            unzipApkOnBack(fileSource, appTask, externalFilesDownloadDir.absolutePath)

        _unzippingTasks.remove(appTask)//解压成功，移除
        return strPathNameApk
    }

    //////////////////////////////////////////////////////////////////

    /**
     * 解压文件
     * @param fileSource 需要解压的文件
     * @param strFilePathDest 目标路径 当前项目中使用的是 /Android/data/包名/Download/文件名/
     */
    @WorkerThread
    private fun unzipNpkOnBack(fileSource: File, strFilePathDest: String): String {
        try {
            val fileNameReal = fileSource.name.getSplitExLast(".", false)//name.subSequence(0, name.lastIndexOf("."))
            val strFilePathDestReal = (strFilePathDest + File.separator + fileNameReal).also { Log.d(TAG, "unzipOnBack: strFilePathDestReal $it") }

            //用来记录apk的文件名
            var apkName = ""
            val zipFile = ZipFile(fileSource)
            val entries = zipFile.entries()
            val bytes = ByteArray(1024 * 1024)
            var zipEntry: ZipEntry?
            while (entries.hasMoreElements()) {
                zipEntry = entries.nextElement() ?: continue
                if (zipEntry.name.contains(MAC__IGNORE)) continue
                if (zipEntry.isDirectory) {
                    File(strFilePathDestReal, zipEntry.name).createFolder()
                    continue
                }
                var tempFile = File(zipEntry.name)

                tempFile.parentFile?.let {//先判断当前文件是否含有路径 如 Android/obb/包名/xx.obb
                    tempFile = File(Environment.getExternalStorageDirectory(), tempFile.absolutePath)//根目录/Android/obb/包名/xx.obb
                } ?: kotlin.run {
                    tempFile = File(strFilePathDestReal, zipEntry.name)//如果保护路径则需要把文件复制到根目录下指定的文件夹中
                }
                tempFile.parentFile?.createFolder()
                Log.d(TAG, "unzipOnBack: tempFilePath ${tempFile.absolutePath}")
                //如果文件已经存在，则删除
                tempFile.deleteFile()
                if (tempFile.name.endsWith(".apk")) {
                    apkName = tempFile.name
                }
                val bufferedOutputStream = BufferedOutputStream(FileOutputStream(tempFile))
                val inputStream = zipFile.getInputStream(zipEntry)
                var len: Int
                while ((inputStream.read(bytes).also { len = it }) != -1) {
                    bufferedOutputStream.write(bytes, 0, len)
                }
                bufferedOutputStream.flushClose()
                inputStream.close()
            }
            zipFile.close()
            return strFilePathDestReal + File.separator + apkName
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "unzipOnBack: error ${e.message}")
//            throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()
            return ""
        }
    }

    /**
     *
     * @param apkFileSource File
     * @param strApkFilePathDest String /storage/emulated/0/data/com.xx.xxx/files/Download/
     */
    @WorkerThread
    private fun unzipApkOnBack(apkFileSource: File, appTask: AppTask, strApkFilePathDest: String): String {
        val strApkFileNameReal = apkFileSource.name.getSplitExLast(".", false)//name.subSequence(0, name.lastIndexOf("."))
        val strApkFilePathDestReal = (strApkFilePathDest + File.separator + strApkFileNameReal).also { Log.d(TAG, "unzipOnBack: strFilePathDestReal $it") }

        try {
            strApkFilePathDestReal.createFolder()
            ///storage/emulated/0/data/com.xx.xxx/files/Download/xxx/

            //用来记录apk的文件名
            var apkFileName = ""
            val zipFile = ZipFile(apkFileSource)
            val entries = zipFile.entries()
//            val bytes = ByteArray(1024 * 1024)
            /////////////////////////////////////////////////////////

            var zipEntry: ZipEntry?
            var totalOffset = 0L
            var totalSize = 0L
            var lastOffset = 0L
            var lastTime = System.currentTimeMillis()
            while (entries.hasMoreElements()) {
                zipEntry = entries.nextElement() ?: continue
                if (zipEntry.name.contains(MAC__IGNORE)) continue
                if (!zipEntry.name.contains("assets")) continue
                if (!zipEntry.name.contains("assets/Android") && !zipEntry.name.endsWith("apk")) continue
                Log.v(TAG, "unzipApkOnBack: assets/Android name ${zipEntry.name}")

                if (zipEntry.isDirectory) {
                    File(strApkFilePathDestReal, zipEntry.name).createFolder()
                    continue
                }

                var tempFile = File(zipEntry.name)

                tempFile = if (tempFile.parentFile != null && tempFile.parentFile!!.absolutePath.contains("Android")) {//先判断当前文件是否含有路径 如 Android/obb/包名/xx.obb
                    File(Environment.getExternalStorageDirectory(), tempFile.absolutePath.replace("assets" + File.separator, ""))//根目录/Android/obb/包名/xx.obb
                } else {
                    File(strApkFilePathDestReal, zipEntry.name.replace("assets" + File.separator, ""))//如果保护路径则需要把文件复制到根目录下指定的文件夹中
                }
                tempFile.parentFile?.createFolder()
                Log.d(TAG, "unzipApkOnBack: tempFilePath ${tempFile.absolutePath}")

                tempFile.deleteFile()//如果文件已经存在，则删除
                if (tempFile.name.endsWith("apk")) {
                    apkFileName = tempFile.name
                    Log.d(TAG, "unzipApkOnBack: apkFileName $apkFileName")
                }

                //移动文件
                zipFile.getInputStream(zipEntry).inputStream2fileOfBufferedOps(tempFile, bufferSize = 1024 * 1024, block = { offset: Int, _: Float ->
                    totalOffset += offset
                    if (System.currentTimeMillis() - lastTime > 1000L) {
                        lastTime = System.currentTimeMillis()
                        totalSize = if (totalOffset > appTask.apkFileSize) totalOffset else appTask.apkFileSize
                        val offsetIndexPerSeconds = totalOffset - lastOffset
                        lastOffset = totalOffset
                        val progress = (totalOffset.toFloat() / totalSize.toFloat()).normalize(0f, 1f) * 100f
                        TaskKHandler.post {
                            onUnziping(appTask, progress.toInt(), totalOffset, totalSize, offsetIndexPerSeconds)
                        }
                    }
                })
//                val bufferedOutputStream = BufferedOutputStream(FileOutputStream(tempFile))
//                var len: Int
//                while ((inputStream.read(bytes).also { len = it }) != -1) {
//                    bufferedOutputStream.write(bytes, 0, len)
//                }
//                bufferedOutputStream.flushClose()
//                inputStream.close()
            }
            zipFile.close()
            if (apkFileName.isEmpty()) {
                strApkFilePathDestReal.deleteFolder()
                return apkFileSource.absolutePath
            }
            return strApkFilePathDestReal + File.separator + apkFileName
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "unzipOnBack: error ${e.message}")
            strApkFilePathDestReal.deleteFolder()
            return apkFileSource.absolutePath
//            throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()
        }
    }
}