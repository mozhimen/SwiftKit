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
import com.mozhimen.basick.utilk.kotlin.collections.containsBy
import com.mozhimen.basick.utilk.kotlin.createFolder
import com.mozhimen.basick.utilk.kotlin.deleteFolder
import com.mozhimen.basick.utilk.kotlin.getSplitExLast
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
object NetKAppUnzipManager : IUtilK {
    /**
     *  过滤在mac上压缩时自动生成的__MACOSX文件夹
     */
    private const val MAC__IGNORE = "__MACOSX/"

    //////////////////////////////////////////////////////////////////

    private val _unzippingTasks = CopyOnWriteArrayList<AppTask>()

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
        if (isUnziping(appTask)) {//正在解压
            Log.d(TAG, "unzip: the task already unziping")
            return
        }
        /**
         * [CNetKAppState.STATE_UNZIPING]
         */
        NetKApp.onUnziping(appTask)

        if (appTask.apkFileName.endsWith(".apk") && !appTask.apkUnzipNeed) {
            onUnzipSuccess(appTask)
        }

        TaskKExecutor.execute(TAG + "unzip") {
            try {
                val strPathNameUnzip = unzipOnBack(appTask)
                Log.d(TAG, "unzip: strPathNameUnzip $$strPathNameUnzip")
                if (strPathNameUnzip.isEmpty())
                    throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()
                else if (!strPathNameUnzip.endsWith("apk"))
                    throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()

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
        /**
         * [CNetKAppState.STATE_UNZIP_SUCCESS]
         */
        NetKApp.onUnzipSuccess(appTask)

//        NetKAppInstallManager.install(appTask, appTask.apkPathName.strFilePath2file())
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
            unzipApkOnBack(fileSource, externalFilesDownloadDir.absolutePath)

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
            val bytes = ByteArray(1024)
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
            throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()
        }
    }

    /**
     *
     * @param apkFileSource File
     * @param strApkFilePathDest String /storage/emulated/0/data/com.xx.xxx/files/Download/
     */
    @WorkerThread
    private fun unzipApkOnBack(apkFileSource: File, strApkFilePathDest: String): String {
        try {
            val strApkFileNameReal = apkFileSource.name.getSplitExLast(".", false)//name.subSequence(0, name.lastIndexOf("."))

            val strApkFilePathDestReal = (strApkFilePathDest + File.separator + strApkFileNameReal).also { Log.d(TAG, "unzipOnBack: strFilePathDestReal $it") }
            strApkFilePathDestReal.createFolder()
            ///storage/emulated/0/data/com.xx.xxx/files/Download/xxx/

            //用来记录apk的文件名
            var apkFileName = ""
            val zipFile = ZipFile(apkFileSource)
            val entries = zipFile.entries()
            val bytes = ByteArray(1024)
            var zipEntry: ZipEntry?
            while (entries.hasMoreElements()) {
                zipEntry = entries.nextElement() ?: continue
                if (zipEntry.name.contains(MAC__IGNORE)) continue
                if (!zipEntry.name.contains("assets")) continue
                if (!zipEntry.name.contains("assets/Android") || !zipEntry.name.endsWith("apk")) continue
                if (zipEntry.isDirectory) {
                    File(strApkFilePathDestReal, zipEntry.name).createFolder()
                    continue
                }
                var tempFile = File(zipEntry.name)

                tempFile.parentFile?.let {//先判断当前文件是否含有路径 如 Android/obb/包名/xx.obb
                    tempFile = File(Environment.getExternalStorageDirectory(), tempFile.absolutePath)//根目录/Android/obb/包名/xx.obb
                } ?: kotlin.run {
                    tempFile = File(strApkFilePathDestReal, zipEntry.name)//如果保护路径则需要把文件复制到根目录下指定的文件夹中
                }
                tempFile.parentFile?.createFolder()
                Log.d(TAG, "unzipOnBack: tempFilePath ${tempFile.absolutePath}")
                //如果文件已经存在，则删除
                tempFile.deleteFile()
                if (tempFile.name.endsWith(".apk")) {
                    apkFileName = tempFile.name
                }
                //移动文件
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
            if (apkFileName.isEmpty()) {
                strApkFilePathDestReal.deleteFolder()
                return apkFileSource.absolutePath
            }
            return strApkFilePathDestReal + File.separator + apkFileName
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "unzipOnBack: error ${e.message}")
            throw CNetKAppErrorCode.CODE_UNZIP_FAIL.intAppErrorCode2appDownloadException()
        }
    }
}