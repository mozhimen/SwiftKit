package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.utilk.android.content.UtilKAssetManager
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2bitmapAny
import com.mozhimen.basick.utilk.java.io.inputStream2bytes_use
import com.mozhimen.basick.utilk.java.io.inputStream2file
import com.mozhimen.basick.utilk.java.io.inputStream2strOfBytes
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBufferedReader
import java.io.File

/**
 * @ClassName UtilKAssetFormat
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/18 23:47
 * @Version 1.0
 */
fun String.getStrAssetName(): String =
    UtilKStrAsset.getStrAssetName(this)

fun String.getStrAssetParentPath(): String =
    UtilKStrAsset.getStrAssetParentPath(this)

///////////////////////////////////////////////////////////////////

fun String.isAssetExists(): Boolean =
    UtilKStrAsset.isAssetExists(this)

///////////////////////////////////////////////////////////////////

fun String.strAssetName2strFilePathName(strFilePathNameDest: String): String =
    UtilKStrAsset.strAssetName2strFilePathName(this, strFilePathNameDest)

fun String.strAssetName2bytes(): ByteArray =
    UtilKStrAsset.strAssetName2bytes(this)

fun String.strAssetName2str_use_ofBufferedReader(): String? =
    UtilKStrAsset.strAssetName2str_use_ofBufferedReader(this)

fun String.strAssetName2strOfBytes(): String? =
    UtilKStrAsset.strAssetName2strOfBytes(this)

fun String.strAssetName2strOfStream(): String? =
    UtilKStrAsset.strAssetName2strOfStream(this)

fun String.strAssetName2file(strFilePathNameDest: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
    UtilKStrAsset.strAssetName2file(this, strFilePathNameDest, isAppend, bufferSize, block)

fun String.strAssetName2bitmap(): Bitmap? =
    UtilKStrAsset.strAssetName2bitmap(this)

///////////////////////////////////////////////////////////////////

object UtilKStrAsset : BaseUtilK() {
    @JvmStatic
    fun getStrAssetName(strAssetName: String): String =
        if (strAssetName.containStr("/")) strAssetName.getSplitLastIndexToEnd("/")
        else ""

    @JvmStatic
    fun getStrAssetParentPath(strAssetName: String): String =
        if (strAssetName.containStr("/")) strAssetName.getSplitLastIndexToStart("/")
        else ""

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAssetExists(strAssetName: String): Boolean {
        val parentPath = getStrAssetParentPath(strAssetName)
        val assets = UtilKAssetManager.list_ofRes(_context, parentPath) ?: return false
        for (index in assets.indices) {
            if ((parentPath + assets[index]) == strAssetName) return true
        }
        return false
    }

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strAssetName2strFilePathName(strAssetName: String, strFilePathNameDest: String): String =
        if (strFilePathNameDest.endsWith("/")) strFilePathNameDest + strAssetName
        else strFilePathNameDest

    @JvmStatic
    fun strAssetName2bytes(strAssetName: String): ByteArray =
        UtilKAssetManager.open_ofCxt(_context, strAssetName).inputStream2bytes_use()

    /**
     * 文件转String:分析json文件,从资产文件加载内容:license,获取txt文本文件内容等
     */
    @JvmStatic
    fun strAssetName2str_use_ofBufferedReader(strAssetName: String): String? =
        if (!isAssetExists(strAssetName)) null
        else UtilKAssetManager.open_ofCxt(_context, strAssetName).inputStream2str_use_ofBufferedReader()

    /**
     * 获取文本文件内容: txt 最快的方法
     */
    @JvmStatic
    fun strAssetName2strOfBytes(strAssetName: String): String? =
        if (!isAssetExists(strAssetName)) null
        else strAssetName2bytes(strAssetName).bytes2str()

    /**
     * 通过路径加载Assets中的文本内容
     */
    @JvmStatic
    fun strAssetName2strOfStream(strAssetName: String): String? =
        if (!isAssetExists(strAssetName)) null
        else UtilKAssetManager.open_ofCxt(_context, strAssetName).inputStream2strOfBytes()

    /**
     * 从资产拷贝到文件
     */
    @JvmStatic
    fun strAssetName2file(strAssetName: String, strFilePathNameDest: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
        if (!isAssetExists(strAssetName)) null
        else UtilKAssetManager.open_ofCxt(_context, strAssetName).inputStream2file(strAssetName.strAssetName2strFilePathName(strFilePathNameDest), isAppend, bufferSize, block)

    @JvmStatic
    fun strAssetName2bitmap(strAssetName: String): Bitmap? =
        if (!isAssetExists(strAssetName)) null
        else UtilKAssetManager.open_ofCxt(_context, strAssetName).inputStream2bitmapAny()
}