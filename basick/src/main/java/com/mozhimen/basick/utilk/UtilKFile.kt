package com.mozhimen.basick.utilk

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import com.mozhimen.basick.logk.LogK
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @ClassName UtilKFile
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 11:59
 * @Version 1.0
 */
object UtilKFile {

    private const val TAG = "UtilKFile>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!

    fun file2Uri(file: File): Uri =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                _context,
                "${_context.packageName}.fileProvider",
                file
            )
        } else {
            Uri.fromFile(file)
        }

    /**
     * 文件转MD5
     * @param inputStream InputStream
     * @return String?
     */
    fun file2MD5(inputStream: InputStream): String? {
        val digest: MessageDigest?
        val buffer = ByteArray(1024)
        var len: Int
        try {
            digest = MessageDigest.getInstance("MD5")
            while (inputStream.read(buffer, 0, 1024).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val bigInteger = BigInteger(1, digest.digest())
        return bigInteger.toString(16)
    }

    /**
     * 删除文件
     * @param file File
     */
    fun deleteFile(file: File) {
        val parentPath = file.parent ?: throw Exception("this file does nt have parent path")
        val tempPath = File(parentPath)
        if (!tempPath.exists()) {
            tempPath.mkdirs()
        }
    }

    /**
     * 删除所有文件
     * @param path String
     */
    fun deleteAllFiles(path: String) {
        deleteAllFiles(File(path))
    }

    /**
     * 删除所有文件
     * @param root File
     */
    fun deleteAllFiles(root: File) {
        if (!root.exists()) return
        Log.d(TAG, "deleteAllFiles: root ${root.absolutePath}")
        val listFiles: Array<File> = root.listFiles() ?: emptyArray()
        if (listFiles.isNotEmpty())
            for (file in listFiles) {
                if (file.isDirectory) { // 判断是否为文件夹
                    deleteAllFiles(file)
                    try {
                        file.delete()
                    } catch (e: Exception) {
                        LogK.et(TAG, "deleteAllFiles: Exception ${e.message}")
                        e.printStackTrace()
                    }
                } else {
                    if (file.exists()) { // 判断是否存在
                        try {
                            file.delete()
                        } catch (e: Exception) {
                            LogK.et(TAG, "deleteAllFiles: Exception ${e.message}")
                            e.printStackTrace()
                        }
                    }
                }
            }
    }
}