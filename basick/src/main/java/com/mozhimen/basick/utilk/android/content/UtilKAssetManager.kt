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
    fun getFromRes(context: Context): AssetManager =
        UtilKResource.getAssets(context)

    @JvmStatic
    fun getFromCxt(context: Context): AssetManager =
        UtilKContext.getAssets(context)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun openFd(filePathWithName: String, context: Context): AssetFileDescriptor =
        getFromCxt(context).openFd(filePathWithName)

    @JvmStatic
    fun open(filePathWithName: String, context: Context): InputStream =
        getFromCxt(context).open(filePathWithName)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun list(assetFileName: String, context: Context): Array<String>? =
        getFromRes(context).list(assetFileName)
}