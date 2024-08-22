package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import java.io.InputStream

/**
 * @ClassName UtilKAssetManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 16:06
 * @Version 1.0
 */
object UtilKAssetManager {
    @JvmStatic
    fun get_ofContext(context: Context): AssetManager =
        UtilKContext.getAssets(context)

    @JvmStatic
    fun get_ofResources(context: Context): AssetManager =
        UtilKResources.getAssets_ofApp(context)//===get_ofContext

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun openFd(context: Context, strAssetName: String): AssetFileDescriptor =
        get_ofContext(context).openFd(strAssetName)

    @JvmStatic
    fun open(context: Context, strAssetName: String): InputStream =
        get_ofContext(context).open(strAssetName)

    @JvmStatic
    fun list(context: Context, strAssetName: String): Array<String>? =
        get_ofContext(context).list(strAssetName)
}