package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.annotation.AnyRes
import androidx.annotation.ArrayRes
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.StyleableRes

/**
 * @ClassName UtilKResource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 15:10
 * @Version 1.0
 */
object UtilKResource {
    @JvmStatic
    fun getSystemResources(): Resources =
        Resources.getSystem()

    @JvmStatic
    fun getAppResources(context: Context): Resources =
        UtilKContext.getResources(context)

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 获取系统Configuration
     * @return Configuration
     */
    @JvmStatic
    fun getSystemConfiguration(): Configuration =
        getConfiguration(getSystemResources())

    @JvmStatic
    fun getConfiguration(resources: Resources): Configuration =
        resources.configuration

    @JvmStatic
    fun getDisplayMetrics(): DisplayMetrics =
        getSystemResources().displayMetrics

    @SuppressLint("DiscouragedApi")
    @JvmStatic
    fun getIdentifier(name: String, defType: String, defPackage: String): Int =
        getSystemResources().getIdentifier(name, defType, defPackage)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getAssets(context: Context): AssetManager =
        getAppResources(context).assets

    @JvmStatic
    fun getResourceEntryName(@AnyRes resId: Int, context: Context): String =
        getAppResources(context).getResourceEntryName(resId)

    @JvmStatic
    fun obtainAttributes(set: AttributeSet, @StyleableRes attrs: IntArray, context: Context): TypedArray =
        getAppResources(context).obtainAttributes(set, attrs)

    @JvmStatic
    fun getDimensionPixelOffset(@DimenRes resId: Int, context: Context): Int =
        getAppResources(context).getDimensionPixelOffset(resId)

    @JvmStatic
    fun getDimensionPixelSize(@DimenRes resId: Int, context: Context): Int =
        getAppResources(context).getDimensionPixelSize(resId)

    @JvmStatic
    fun getDimension(@DimenRes resId: Int, context: Context): Float =
        getAppResources(context).getDimension(resId)

    @JvmStatic
    fun getStringArray(@ArrayRes resId: Int, context: Context): Array<String> =
        getAppResources(context).getStringArray(resId)

    @JvmStatic
    fun getInteger(@IntegerRes resId: Int, context: Context): Int =
        getAppResources(context).getInteger(resId)
}