package com.mozhimen.basick.utilk

import android.util.Log
import com.mozhimen.basick.utilk.context.UtilKApplication
import java.io.*


/**
 * @ClassName UtilKAsset
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 3:52
 * @Version 1.0
 */
object UtilKAsset {
    private const val TAG = "UtilKAssets>>>>>"
    private const val msg_not_exist = "fail, make sure it's file or exist"
    private const val msg_wrong = "something wrong"
    private val _context = UtilKApplication.instance.get()

    /**
     * 文件是否存在
     * @param assetFileName String
     * @return Boolean
     */
    @JvmStatic
    fun isFileExists(assetFileName: String): Boolean {
        val listAssetFiles = _context.assets.list("")
        val tmpAssetFileName = assetFileName.trim()
        for (index in listAssetFiles!!.indices) {
            if (listAssetFiles[index] == tmpAssetFileName) return true
        }
        return false
    }

    /**
     * 文件转String:分析json文件,从资产文件加载内容:license,获取txt文本文件内容等
     * @param assetFileName String
     * @return String
     */
    @JvmStatic
    fun file2String(assetFileName: String): String {
        if (!isFileExists(assetFileName)) return msg_not_exist
        val inputStream = _context.assets.open(assetFileName)
        try {
            return UtilKFile.inputStream2String(inputStream).replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }

    /**
     * 获取文本文件内容: txt
     * 最快的方法
     * @param assetFileName String
     * @return String
     */
    @JvmStatic
    fun file2String2(assetFileName: String): String {
        if (!isFileExists(assetFileName)) return msg_not_exist
        val inputStream = _context.assets.open(assetFileName)
        try {
            val data = ByteArray(inputStream.available())
            inputStream.read(data)
            return String(data).replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }

    /**
     * 通过路径加载Assets中的文本内容
     * @param assetFileName String
     * @return String
     */
    @JvmStatic
    fun file2String3(assetFileName: String): String {
        if (!isFileExists(assetFileName)) return msg_not_exist
        val inputStream = _context.resources.assets.open(assetFileName)
        val stringBuilder = StringBuilder()
        try {
            var bufferLength: Int
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer).also { bufferLength = it } != -1) {
                stringBuilder.append(String(buffer, 0, bufferLength))
            }
            return stringBuilder.toString().replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }

    /**
     * 从资产拷贝到文件
     * @param assetFileName String
     * @param destFilePathWithName String
     * @return String
     */
    @JvmStatic
    fun asset2File(assetFileName: String, destFilePathWithName: String, isOverwrite: Boolean = true): String {
        if (!isFileExists(assetFileName)) return msg_not_exist
        val inputStream: InputStream = _context.assets.open(assetFileName)
        //整理名称
        var tmpDestFilePath = destFilePathWithName
        if (tmpDestFilePath.endsWith("/")) {
            tmpDestFilePath += assetFileName
        }
        Log.d(TAG, "assetCopyFile: tmpDestFilePath $tmpDestFilePath")
        try {
            return UtilKFile.inputStream2File(inputStream, tmpDestFilePath, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }
}