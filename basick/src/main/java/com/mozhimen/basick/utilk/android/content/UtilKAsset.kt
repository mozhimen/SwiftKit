package com.mozhimen.basick.utilk.android.content

import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.containStr
import com.mozhimen.basick.utilk.kotlin.getSplitExLast


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

    @JvmStatic
    fun getStrAssetFileParentPath(destFilePathWithName: String): String =
        if (destFilePathWithName.containStr("/")) destFilePathWithName.getSplitExLast("/") else ""

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAssetExists(assetFileName: String): Boolean {
        val parentPath = getStrAssetFileParentPath(assetFileName)
        val assets = UtilKAssetManager.list(parentPath, _context) ?: return false
        for (index in assets.indices) {
            if ((parentPath + assets[index]) == assetFileName) return true
        }
        return false
    }

}