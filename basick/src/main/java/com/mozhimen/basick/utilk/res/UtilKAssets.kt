package com.mozhimen.basick.utilk.res

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.util.Log
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.log.et
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import java.io.*


/**
 * @ClassName UtilKAsset
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 3:52
 * @Version 1.0
 */
object UtilKAssets : BaseUtilK() {

    @JvmStatic
    fun openFd(filePathWithName: String): AssetFileDescriptor =
        getAssets2().openFd(filePathWithName)

    @JvmStatic
    fun open(filePathWithName: String): InputStream =
        getAssets().open(filePathWithName)

    /**
     * 获取App的Assets
     * @return AssetManager
     */
    @JvmStatic
    fun getAssets(): AssetManager =
        UtilKRes.getAppResources().assets

    /**
     * 获取App的Assets
     * @return AssetManager
     */
    @JvmStatic
    fun getAssets2(): AssetManager =
        UtilKContext.getAssets(UtilKApplication.instance.get())

    /**
     * 获取App的Assets
     * @return AssetManager
     */
    @JvmStatic
    fun getAssets(context: Context): AssetManager =
        UtilKContext.getAssets(context)

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
    fun asset2Str(assetName: String): String {
        if (!isAssetExists(assetName)) return CMsg.NOT_EXIST
        val inputStream = getAssets().open(assetName)
        try {
            return UtilKFile.inputStream2Str(inputStream).replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream.close()
        }
        return CMsg.WRONG
    }

    /**
     * 获取文本文件内容: txt
     * 最快的方法
     * @param assetName String
     * @return String
     */
    @JvmStatic
    fun asset2Str2(assetName: String): String {
        if (!isAssetExists(assetName)) return CMsg.NOT_EXIST
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
        return CMsg.WRONG
    }

    /**
     * 通过路径加载Assets中的文本内容
     * @param assetName String
     * @return String
     */
    @JvmStatic
    fun asset2Str3(assetName: String): String {
        if (!isAssetExists(assetName)) return CMsg.NOT_EXIST
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
        return CMsg.WRONG
    }

    /**
     * 从资产拷贝到文件
     * @param assetName String
     * @param destFilePathWithName String
     * @return String
     */
    @JvmStatic
    fun asset2File(assetName: String, destFilePathWithName: String, isOverwrite: Boolean = true): File? {
        if (!isAssetExists(assetName)) return null
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
        return null
    }
}