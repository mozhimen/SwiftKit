package com.mozhimen.basick.utilk

import android.text.TextUtils
import java.io.*
import android.content.res.AssetManager
import android.util.Log
import com.mozhimen.basick.logk.LogK


/**
 * @ClassName UtilKAsset
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 3:52
 * @Version 1.0
 */
object UtilKAssets {
    private val TAG = "UtilKAssets>>>>>"

    /**
     * 分析json文件
     * @param fileName String
     * @return String?
     */
    fun json2String(fileName: String): String? {
        val assets = UtilKGlobal.instance.getApp()!!.assets
        try {
            val inputStream = assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            val builder = StringBuilder()
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let { builder.append(it) }
            }
            inputStream.close()
            bufferedReader.close()
            return builder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 文件是否存在
     * @param filename String
     * @return Boolean
     */
    fun isFileExists(filename: String): Boolean {
        val assetManager: AssetManager = UtilKGlobal.instance.getApp()!!.assets
        try {
            val names = assetManager.list("")
            for (i in names!!.indices) {
                if (names[i] == filename.trim()) {
                    return true
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return false
    }

    /**
     * 从资产文件加载内容:license
     * @param licenseFileName String?
     * @return String?
     */
    fun license2String(licenseFileName: String?): String? {
        var inputStream: InputStream? = null
        try {
            inputStream = UtilKGlobal.instance.getApp()!!.assets.open(licenseFileName!!)
            return UtilKFile.inputStream2String(inputStream)
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
     * @param path String?
     * @return String?
     */
    fun txt2String(path: String): String? {
        try {
            val stream = UtilKGlobal.instance.getApp()!!.assets.open(path)
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

    /**
     * 从资产拷贝到文件
     * @param fileName String?
     * @param destFilePath String
     * @return String?
     */
    fun assets2File(fileName: String, destFilePath: String): String? {
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
                val assetFileMD5 = UtilKFile.file2MD5(assetManager.open(fileName))
                val destFileMD5 = UtilKFile.file2MD5(FileInputStream(destFile))
                if (TextUtils.equals(assetFileMD5, destFileMD5)) {
                    return destAbsolutePath
                }
            }
            val parentFile = destFile.parentFile
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            destFile.createNewFile()
            out = FileOutputStream(destFile)
            val buffer = ByteArray(1024)
            var read: Int
            inputStream = assetManager.open(fileName)
            while (inputStream.read(buffer).also { read = it } != -1) {
                out.write(buffer, 0, read)
            }
        } catch (e: Exception) {
            LogK.et(TAG, "assets2File Exception ${e.message}")
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
        Log.d(TAG, "assets2File destFilePath $destFilePath")
        return destAbsolutePath
    }
}