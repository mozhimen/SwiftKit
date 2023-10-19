package com.mozhimen.basick.utilk.kotlin

import android.content.Context
import android.graphics.Bitmap
import com.mozhimen.basick.utilk.android.content.UtilKAsset
import com.mozhimen.basick.utilk.android.content.UtilKAssetManager
import com.mozhimen.basick.utilk.android.content.getStrAssetFilePath
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2anyBitmap
import com.mozhimen.basick.utilk.java.io.inputStream2bytes
import com.mozhimen.basick.utilk.java.io.inputStream2file
import com.mozhimen.basick.utilk.java.io.inputStream2str
import com.mozhimen.basick.utilk.java.io.inputStream2str3
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import java.io.File

/**
 * @ClassName UtilKAssetFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/18 23:47
 * @Version 1.0
 */
fun String.strAssetFileName2bytes(): ByteArray =
    UtilKStrAssetFileName.strAssetFileName2bytes(this)

fun String.strAssetFileName2str(): String? =
    UtilKStrAssetFileName.strAssetFileName2str(this)

fun String.strAssetFileName2str2(): String? =
    UtilKStrAssetFileName.strAssetFileName2str2(this)

fun String.strAssetFileName2str3(): String? =
    UtilKStrAssetFileName.strAssetFileName2str3(this)

fun String.strAssetFileName2file(destFilePathWithName: String, isAppend: Boolean = false): File? =
    UtilKStrAssetFileName.strAssetFileName2file(this, destFilePathWithName, isAppend)

fun String.strAssetFileName2bitmap(): Bitmap? =
    UtilKStrAssetFileName.strAssetFileName2bitmap(this)

object UtilKStrAssetFileName : BaseUtilK() {

    @JvmStatic
    fun strAssetFileName2bytes(assetFileName: String): ByteArray =
        UtilKAssetManager.open(assetFileName, _context).inputStream2bytes()

    /**
     * 文件转String:分析json文件,从资产文件加载内容:license,获取txt文本文件内容等
     */
    @JvmStatic
    fun strAssetFileName2str(assetFileName: String): String? =
        if (!UtilKAsset.isAssetExists(assetFileName)) null
        else UtilKAssetManager.open(assetFileName, _context).inputStream2str()?.replaceRegexLineBreak()

    /**
     * 获取文本文件内容: txt 最快的方法
     */
    @JvmStatic
    fun strAssetFileName2str2(assetFileName: String): String? =
        if (!UtilKAsset.isAssetExists(assetFileName)) null
        else strAssetFileName2bytes(assetFileName).bytes2str().replaceRegexLineBreak()

    /**
     * 通过路径加载Assets中的文本内容
     */
    @JvmStatic
    fun strAssetFileName2str3(assetFileName: String): String? =
        if (!UtilKAsset.isAssetExists(assetFileName)) null
        else UtilKAssetManager.open(assetFileName, _context).inputStream2str3()?.replaceRegexLineBreak()

    /**
     * 从资产拷贝到文件
     */
    @JvmStatic
    fun strAssetFileName2file(assetFileName: String, destFilePathWithName: String, isAppend: Boolean = false): File? =
        if (!UtilKAsset.isAssetExists(assetFileName)) null
        else UtilKAssetManager.open(assetFileName, _context).inputStream2file(destFilePathWithName.getStrAssetFilePath(assetFileName), isAppend)

    /**
     *
     */
    @JvmStatic
    fun strAssetFileName2bitmap(assetFileName: String): Bitmap? =
        if (!UtilKAsset.isAssetExists(assetFileName)) null
        else UtilKAssetManager.open(assetFileName, _context).inputStream2anyBitmap()
}