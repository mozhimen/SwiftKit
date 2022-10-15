package com.mozhimen.basick.utilk

import android.text.TextUtils
import java.io.*


/**
 * @ClassName UtilKAsset
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 3:52
 * @Version 1.0
 */
object UtilKAssets {
    private const val TAG = "UtilKAssets>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!

    /**
     * 文件是否存在
     * @param assetFileName String
     * @return Boolean
     */
    @JvmStatic
    @Throws(Exception::class)
    fun isFileExists(assetFileName: String): Boolean {
        val assetFileNames = _context.assets.list("")
        for (index in assetFileNames!!.indices) {
            if (assetFileNames[index] == assetFileName.trim()) return true
        }
        return false
    }

    /**
     * 文件转String:分析json文件,从资产文件加载内容:license,获取txt文本文件内容等
     * @param assetFileName String
     * @return String
     */
    @JvmStatic
    @Throws(Exception::class)
    fun file2String(assetFileName: String): String {
        val inputStream = _context.assets.open(assetFileName)
        inputStream.use { stream ->
            return UtilKFile.inputStream2String(stream)
        }
    }

    /**
     * 获取文本文件内容: txt
     * @param assetFileName String
     * @return String
     */
    @JvmStatic
    @Throws(Exception::class)
    fun txt2String(assetFileName: String): String {
        val inputStream = _context.assets.open(assetFileName)
        inputStream.use { stream ->
            val length = stream.available()
            val data = ByteArray(length)
            stream.read(data)
            return String(data)
        }
    }

    /**
     * 通过路径加载Assets中的文本内容
     * @param assetFileName String
     * @return String
     */
    @JvmStatic
    @Throws(Exception::class)
    fun txt2String2(assetFileName: String): String {
        val stringBuilder = StringBuilder()
        val inputStream = _context.resources.assets.open(assetFileName)
        inputStream.use { stream ->
            var bufferLength: Int
            val buffer = ByteArray(1024)
            while (stream.read(buffer).also { bufferLength = it } != -1) {
                stringBuilder.append(String(buffer, 0, bufferLength))
            }
            return stringBuilder.toString().replace("\\r\\n".toRegex(), "\n")
        }
    }

    /**
     * 从资产拷贝到文件
     * @param assetFileName String
     * @param destFilePathWithName String
     * @return String?
     */
    fun assetCopyFile(assetFileName: String, destFilePathWithName: String): String {
        var tmpFilePathWithName = destFilePathWithName
        if (tmpFilePathWithName.endsWith("/")) {
            tmpFilePathWithName += assetFileName
        }
        val inputStream: InputStream = _context.assets.open(assetFileName)
        val destFile = File(destFilePathWithName)
        val fileInputStream = FileInputStream(destFile)
        if (!destFile.exists()) {
            UtilKFile.createFile(destFile)
        } else {
            val assetFileMD5 = UtilKFile.file2Md5(inputStream)
            val destFileMD5 = UtilKFile.file2Md5(fileInputStream)
            if (TextUtils.equals(assetFileMD5, destFileMD5)) {
                return tmpFilePathWithName
            }
        }
        val fileOutputStream = FileOutputStream(destFile)
        try {
            val buffer = ByteArray(1024)
            var bufferLength: Int
            while (inputStream.read(buffer).also { bufferLength = it } != -1) {
                fileOutputStream.write(buffer, 0, bufferLength)
            }
            return tmpFilePathWithName
        } finally {
            fileOutputStream.close()
            fileInputStream.close()
            inputStream.close()
        }
    }
}