package com.mozhimen.basick.utilk.android.content

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.util.Log
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2bytes
import com.mozhimen.basick.utilk.java.io.inputStream2file
import com.mozhimen.basick.utilk.java.io.inputStream2str
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import java.io.File
import java.io.InputStream


/**
 * @ClassName UtilKAsset
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 3:52
 * @Version 1.0
 */
@AManifestKRequire(CApplication.REQUEST_LEGACY_EXTERNAL_STORAGE)
object UtilKAsset : BaseUtilK() {

    @JvmStatic
    fun getForRes(): AssetManager =
            UtilKAssetManager.getForRes(_context)

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun openFd(assetFileName: String): AssetFileDescriptor =
            UtilKAssetManager.openFd(assetFileName, _context)

    @JvmStatic
    fun open(assetFileName: String): InputStream =
            UtilKAssetManager.open(assetFileName, _context)

    @JvmStatic
    fun list(assetFileName: String): Array<String>? =
            UtilKAssetManager.list(assetFileName, _context)

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAssetExists(assetFileName: String): Boolean {
        val assets = list("") ?: return false
        for (index in assets.indices) {
            if (assets[index] == assetFileName) return true
        }
        return false
    }

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun asset2bytes(assetFileName: String): ByteArray =
            open(assetFileName).use { it.inputStream2bytes() }

    /**
     * 文件转String:分析json文件,从资产文件加载内容:license,获取txt文本文件内容等
     * @param assetFileName String
     * @return String
     */
    @JvmStatic
    fun asset2str(assetFileName: String): String =
            if (!isAssetExists(assetFileName)) ""
            else open(assetFileName).use { it.inputStream2str().replaceRegexLineBreak() }

    /**
     * 获取文本文件内容: txt
     * 最快的方法
     * @param assetName String
     * @return String
     */
    @JvmStatic
    fun asset2str2(assetFileName: String): String =
            if (!isAssetExists(assetFileName)) ""
            else asset2bytes(assetFileName).bytes2str().replaceRegexLineBreak()

    /**
     * 通过路径加载Assets中的文本内容
     * @param assetName String
     * @return String
     */
    @JvmStatic
    fun asset2str3(assetFileName: String): String {
        if (!isAssetExists(assetFileName)) return CMsg.NOT_EXIST
        val inputStream = open(assetFileName)
        val stringBuilder = StringBuilder()
        try {
            var bufferLength: Int
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer).also { bufferLength = it } != -1) {
                stringBuilder.append(String(buffer, 0, bufferLength))
            }
            return stringBuilder.toString().replaceRegexLineBreak()
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
    fun asset2file(assetFileName: String, destFilePathWithName: String, isOverwrite: Boolean = true): File? {
        if (!isAssetExists(assetFileName)) return null
        val inputStream: InputStream = getForRes().open(assetFileName)
        //整理名称
        var tmpDestFilePath = destFilePathWithName
        if (tmpDestFilePath.endsWith("/")) {
            tmpDestFilePath += assetFileName
        }
        Log.d(TAG, "assetCopyFile: tmpDestFilePath $tmpDestFilePath")
        try {
            return inputStream.inputStream2file(tmpDestFilePath, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream.close()
        }
        return null
    }
}