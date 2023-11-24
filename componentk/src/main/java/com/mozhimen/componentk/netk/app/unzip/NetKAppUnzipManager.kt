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
import com.mozhimen.basick.utilk.java.util.UtilKZip
import com.mozhimen.basick.utilk.kotlin.collections.containsBy
import com.mozhimen.basick.utilk.kotlin.getSplitExLast
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppErrorCode
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import com.mozhimen.componentk.netk.app.download.mos.int2appDownloadException
import com.mozhimen.componentk.netk.app.install.NetKAppInstallManager
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

        TaskKExecutor.execute(TAG + "unzip") {
            try {
                val strPathNameUnzip = unzipOnBack(appTask)
                if (strPathNameUnzip.isEmpty())
                    throw CNetKAppErrorCode.CODE_UNZIP_FAIL.int2appDownloadException()

                TaskKHandler.post {
                    onUnzipSuccess(appTask)
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
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: run {
            throw CNetKAppErrorCode.CODE_UNZIP_DIR_NULL.int2appDownloadException()
        }
        val strPathNameApk = unzipOnBack(File(externalFilesDir, appTask.apkName), externalFilesDir.absolutePath)
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
    private fun unzipOnBack(fileSource: File, strFilePathDest: String): String {
        try {
            val fileNameReal = fileSource.name.getSplitExLast(".", false)//name.subSequence(0, name.lastIndexOf("."))
            val strFilePathDestReal = (strFilePathDest + File.separator + fileNameReal).also { Log.d(UtilKZip.TAG, "unzip: strFilePathDestReal $it") }

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
                var temp = File(zipEntry.name)

                temp.parentFile?.let {//先判断当前文件是否含有路径 如 Android/obb/包名/xx.obb
                    temp = File(Environment.getExternalStorageDirectory(), temp.absolutePath)//根目录/Android/obb/包名/xx.obb
                } ?: kotlin.run {
                    temp = File(strFilePathDestReal, zipEntry.name)//如果保护路径则需要把文件复制到根目录下指定的文件夹中
                }
                temp.parentFile?.createFolder()
                //如果文件已经存在，则删除
                temp.deleteFile()
                if (temp.name.endsWith(".apk")) {
                    apkName = temp.name
                }
                val bufferedOutputStream = BufferedOutputStream(FileOutputStream(temp))
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
            return ""
        }
    }
}