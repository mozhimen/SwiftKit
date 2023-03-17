package com.mozhimen.basick.utilk.res

import android.content.res.AssetManager
import android.util.Log
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
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

    /**
     * 获取App的Assets
     * @return AssetManager
     */
    @JvmStatic
    fun getAssets(): AssetManager =
        UtilKRes.getAppResource().assets

    /**
     * 获取App的Assets
     * @return AssetManager
     */
    @JvmStatic
    fun getAssets2(): AssetManager =
        UtilKApplication.instance.get().assets

    /**
     * Asset是否存在
     * @param assetFileName String
     * @return Boolean
     */
    @JvmStatic
    fun isAssetExists(assetFileName: String): Boolean {
        val listAssets = getAssets().list("")
        val tmpAssetName = assetFileName.trim()
        for (index in listAssets!!.indices) {
            if (listAssets[index] == tmpAssetName) return true
        }
        return false
    }

    /**
     * 文件转String:分析json文件,从资产文件加载内容:license,获取txt文本文件内容等
     * @param assetName String
     * @return String
     */
    @JvmStatic
    fun asset2String(assetName: String): String {
        if (!isAssetExists(assetName)) return msg_not_exist
        val inputStream = getAssets().open(assetName)
        try {
            return UtilKFile.inputStream2String(inputStream).replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }

    /**
     * 获取文本文件内容: txt
     * 最快的方法
     * @param assetName String
     * @return String
     */
    @JvmStatic
    fun asset2String2(assetName: String): String {
        if (!isAssetExists(assetName)) return msg_not_exist
        val inputStream = getAssets().open(assetName)
        try {
            val data = ByteArray(inputStream.available())
            inputStream.read(data)
            return String(data).replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }

    /**
     * 通过路径加载Assets中的文本内容
     * @param assetName String
     * @return String
     */
    @JvmStatic
    fun asset2String3(assetName: String): String {
        if (!isAssetExists(assetName)) return msg_not_exist
        val inputStream = getAssets().open(assetName)
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
            e.message?.et(TAG)
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }

    /**
     * 从资产拷贝到文件
     * @param assetName String
     * @param destFilePathWithName String
     * @return String
     */
    @JvmStatic
    fun asset2File(assetName: String, destFilePathWithName: String, isOverwrite: Boolean = true): String {
        if (!isAssetExists(assetName)) return msg_not_exist
        val inputStream: InputStream = getAssets().open(assetName)
        //整理名称
        var tmpDestFilePath = destFilePathWithName
        if (tmpDestFilePath.endsWith("/")) {
            tmpDestFilePath += assetName
        }
        Log.d(TAG, "assetCopyFile: tmpDestFilePath $tmpDestFilePath")
        try {
            return UtilKFile.inputStream2File(inputStream, tmpDestFilePath, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream.close()
        }
        return msg_wrong
    }
}