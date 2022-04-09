package com.mozhimen.basicsk.utilk

import android.content.Context
import android.text.TextUtils
import android.util.Log
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
    /**
     * load content from assets file : license
     */
    fun getAssetsLicenseContent(licenseFileName: String?): String? {
        var inputStream: InputStream? = null
        try {
            inputStream = UtilKGlobal.instance.getApp()!!.assets.open(licenseFileName!!)
            return inputStream2String(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    /**
     * 获取文本文件内容: txt
     */
    fun getAssetsTxtContent(path: String?): String? {
        try {
            val stream = UtilKGlobal.instance.getApp()!!.assets.open(path!!)
            val length = stream.available()
            val data = ByteArray(length)
            stream.read(data)
            stream.close()
            return String(data)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun copyAssetsToFile(fileName: String?, destFilePath: String): String? {
        var destAbsolutePath: String? = StringBuilder()
            .append(destFilePath)
            .append(if (destFilePath.endsWith("/")) "" else "/")
            .append(fileName)
            .toString()
        val assetManager = UtilKGlobal.instance.getApp()!!.assets
        var inputStream: InputStream? = null
        var out: OutputStream? = null
        try {
            val destFile = File(destAbsolutePath)
            // 如果文件存在且MD5一样则需要进行复制
            if (destFile.exists()) {
                val assetFileMD5 = file2MD5(assetManager.open(fileName!!))
                val destFileMD5 = file2MD5(FileInputStream(destFile))
                if (TextUtils.equals(assetFileMD5, destFileMD5)) {
                    return destAbsolutePath
                }
            }
            val parentFile = destFile.parentFile
            if (!parentFile.exists()) {
                parentFile.kdirs()
            }
            destFile.createNewFile()
            out = FileOutputStream(destFile)
            val buffer = ByteArray(1024)
            var read: Int
            inputStream = assetManager.open(fileName!!)
            while (inputStream.read(buffer).also { read = it } != -1) {
                out.write(buffer, 0, read)
            }
        } catch (e: Exception) {
            Log.e("FileUtil", e.localizedMessage)
            e.printStackTrace()
            destAbsolutePath = null
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    destAbsolutePath = null
                }
            }
            if (out != null) {
                try {
                    out.flush()
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    destAbsolutePath = null
                }
            }
        }
        Log.d("destFilePath", "destFilePath:::$destFilePath")
        return destAbsolutePath
    }

    fun file2MD5(inputStream: InputStream): String? {
        var digest: MessageDigest?
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

    fun deleteFile(file: File) {
        val tempPath = File(file.parent)
        if (!tempPath.exists()) {
            tempPath.kdirs()
        }
    }
}