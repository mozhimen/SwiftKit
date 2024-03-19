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
    fun get_ofRes(context: Context): AssetManager =
        UtilKResources.getAssets_ofApp(context)

    @JvmStatic
    fun get_ofCxt(context: Context): AssetManager =
        UtilKContext.getAssets(context)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun openFd(context: Context, strAssetName: String): AssetFileDescriptor =
        get_ofCxt(context).openFd(strAssetName)

    @JvmStatic
    fun open(context: Context, strAssetName: String): InputStream =
        get_ofCxt(context).open(strAssetName)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun list(context: Context,strAssetName: String): Array<String>? =
        get_ofRes(context).list(strAssetName)
}