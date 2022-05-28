package com.mozhimen.basick.utilk

import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
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

    private val context = UtilKGlobal.instance.getApp()!!
    fun file2Uri(file: File): Uri =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileProvider",
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
     * 流转字符串
     * @param inputStream InputStream
     * @return String?
     */
    fun inputStream2String(inputStream: InputStream): String? {
        var bufferedReader: BufferedReader? = null
        try {
            val buf = StringBuilder()
            bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var str: String?
            while (bufferedReader.readLine().also { str = it } != null) {
                buf.append(str)
                buf.append("\n")
            }
            return buf.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    /**
     * 删除文件
     * @param file File
     */
    fun deleteFile(file: File) {
        val parentPath = file.parent ?: throw Exception("this file doesnt have parent path")
        val tempPath = File(parentPath)
        if (!tempPath.exists()) {
            tempPath.mkdirs()
        }
    }
}