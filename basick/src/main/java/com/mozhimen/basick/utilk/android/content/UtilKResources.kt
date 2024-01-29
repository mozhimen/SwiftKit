package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.annotation.AnyRes
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StyleableRes

/**
 * @ClassName UtilKResource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 15:10
 * @Version 1.0
 */
object UtilKResources {

    @JvmStatic
    fun getSys(): Resources =
        Resources.getSystem()

    //获取系统Configuration
    @JvmStatic
    fun getSysConfiguration(): Configuration =
        getSys().configuration

    @JvmStatic
    fun getSysDisplayMetrics(): DisplayMetrics =
        getDisplayMetrics(getSys())

    @JvmStatic
    @SuppressLint("DiscouragedApi")
    fun getSysIdentifier(name: String, defType: String, defPackage: String): Int =
        getSys().getIdentifier(name, defType, defPackage)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getApp(context: Context): Resources =
        UtilKContext.getResources(context)

    @JvmStatic
    fun getAppConfiguration(context: Context): Configuration =
        getConfiguration(getApp(context))

    @JvmStatic
    fun getAppDisplayMetrics(context: Context): DisplayMetrics =
        getDisplayMetrics(getApp(context))

    @JvmStatic
    fun getAppColor(context: Context, @ColorRes resId: Int): Int =
        getApp(context).getColor(resId)

    @JvmStatic
    @SuppressLint("UseCompatLoadingForDrawables")
    fun getAppDrawable(context: Context, @DrawableRes resId: Int): Drawable =
        getApp(context).getDrawable(resId)// ResourcesCompat.getDrawable()

    @JvmStatic
    fun getAppAssets(context: Context): AssetManager =
        getApp(context).assets

    @JvmStatic
    fun getAppResourceEntryName(context: Context, @AnyRes resId: Int): String =
        getApp(context).getResourceEntryName(resId)

    @JvmStatic
    fun getAppDimensionPixelOffset(context: Context, @DimenRes resId: Int): Int =
        getApp(context).getDimensionPixelOffset(resId)

    @JvmStatic
    fun getAppDimensionPixelSize(context: Context, @DimenRes resId: Int): Int =
        getApp(context).getDimensionPixelSize(resId)

    @JvmStatic
    fun getAppDimension(context: Context, @DimenRes resId: Int): Float =
        getApp(context).getDimension(resId)

    @JvmStatic
    fun getAppStringArray(context: Context, @ArrayRes resId: Int): Array<String> =
        getApp(context).getStringArray(resId)

    @JvmStatic
    fun getAppInteger(context: Context, @IntegerRes resId: Int): Int =
        getApp(context).getInteger(resId)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getConfiguration(resources: Resources): Configuration =
        resources.configuration

    @JvmStatic
    fun getDisplayMetrics(resources: Resources): DisplayMetrics =
        resources.displayMetrics

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun obtainAttributes(context: Context, set: AttributeSet, @StyleableRes attrs: IntArray): TypedArray =
        getApp(context).obtainAttributes(set, attrs)

    @JvmStatic
    fun updateConfiguration(context: Context, configuration: Configuration, displayMetrics: DisplayMetrics) {
        getApp(context).updateConfiguration(configuration, displayMetrics)
    }
}