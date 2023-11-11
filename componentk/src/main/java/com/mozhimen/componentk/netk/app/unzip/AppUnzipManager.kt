package com.mozhimen.componentk.netk.app.unzip

import android.os.Environment
import android.util.Log
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.createFolder
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.util.UtilKZip
import com.mozhimen.basick.utilk.kotlin.collections.containsBy
import com.mozhimen.basick.utilk.kotlin.getSplitExLast
import com.mozhimen.componentk.netk.app.task.db.AppTask
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * @ClassName AppZipManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 15:09
 * @Version 1.0
 */
object AppUnzipManager {
    /**
     *  过滤在mac上压缩时自动生成的__MACOSX文件夹
     */
    private const val MAC__IGNORE = "__MACOSX/"

    //////////////////////////////////////////////////////////////////

    private val _unzippingTasks = mutableListOf<AppTask>()

    //////////////////////////////////////////////////////////////////

    /**
     * 判断当前应用是否在解压过程中
     * @return true 表示正在解压中 false 不在解压中
     */
    @JvmStatic
    fun isUnziping(appTask: AppTask): Boolean {
//        for (task in _unzippingTasks) {
//            if (isSame(appTask, task))
//                return true
//        }
        return _unzippingTasks.containsBy { it.taskId == appTask.taskId } && appTask.isUnziping()
    }

    @JvmStatic
    fun hasUnziping(): Boolean {
        return _unzippingTasks.isNotEmpty()
    }

    @JvmStatic
    fun unzip(appTask: AppTask): String {
        if (isUnziping(appTask)) return ""//正在解压
        _unzippingTasks.add(appTask)//开始解压，添加到列表中
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: return ""
        val strPathNameApk = unzip(File(externalFilesDir, appTask.apkName), externalFilesDir.absolutePath)
        _unzippingTasks.remove(appTask)//解压成功，移除
        return strPathNameApk
    }

    //////////////////////////////////////////////////////////////////

    /**
     * 解压文件
     * @param fileSource 需要解压的文件
     * @param strFilePathDest 目标路径 当前项目中使用的是 /Android/data/包名/Download/文件名/
     */
    private fun unzip(fileSource: File, strFilePathDest: String): String {
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