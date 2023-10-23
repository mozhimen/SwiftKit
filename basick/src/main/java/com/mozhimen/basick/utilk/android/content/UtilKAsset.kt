package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.graphics.Bitmap
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2bytes
import com.mozhimen.basick.utilk.java.io.inputStream2file
import com.mozhimen.basick.utilk.java.io.inputStream2str
import com.mozhimen.basick.utilk.java.io.inputStream2str3
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import java.io.File


/**
 * @ClassName UtilKAsset
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 3:52
 * @Version 1.0
 */
fun String.getStrAssetFilePath(assetFileName: String): String =
    UtilKAsset.getStrAssetFilePath(this, assetFileName)

@AManifestKRequire(CApplication.REQUEST_LEGACY_EXTERNAL_STORAGE)
object UtilKAsset : BaseUtilK() {

    @JvmStatic
    fun getStrAssetFilePath(destFilePathWithName: String, assetFileName: String): String =
        if (destFilePathWithName.endsWith("/")) destFilePathWithName + assetFileName else destFilePathWithName

    ///////////////////////////////////////////////////////////////////

    @JvmStatic

    fun isAssetExists(assetFileName: String): Boolean {
        val assets = UtilKAssetManager.list("", _context) ?: return false
        for (index in assets.indices) {
            if (assets[index] == assetFileName) return true
        }
        return false
    }

    ///////////////////////////////////////////////////////////////////


}