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
        UtilKResource.getAssets(context)

    @JvmStatic
    fun getForContext(context: Context): AssetManager =
        UtilKContext.getAssets(context)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun openFd(filePathWithName: String, context: Context): AssetFileDescriptor =
        getForContext(context).openFd(filePathWithName)

    @JvmStatic
    fun open(filePathWithName: String, context: Context): InputStream =
        getForContext(context).open(filePathWithName)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun list(assetFileName: String, context: Context): Array<String>? =
        getForRes(context).list(assetFileName)
}