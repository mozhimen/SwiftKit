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
    fun getForRes(context: Context): AssetManager =
        UtilKResources.getAppAssets(context)

    @JvmStatic
    fun getForContext(context: Context): AssetManager =
        UtilKContext.getAssets(context)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun openFd(context: Context, strAssetName: String): AssetFileDescriptor =
        getForContext(context).openFd(strAssetName)

    @JvmStatic
    fun open(context: Context, strAssetName: String): InputStream =
        getForContext(context).open(strAssetName)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun list(context: Context,strAssetName: String): Array<String>? =
        getForRes(context).list(strAssetName)
}