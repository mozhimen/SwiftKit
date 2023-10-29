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

    @JvmStatic
    @SuppressLint("DiscouragedApi")
    fun getIdentifier(name: String, defType: String, defPackage: String): Int =
        getSystemResources().getIdentifier(name, defType, defPackage)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(context: Context, @DrawableRes id: Int): Drawable =
        getAppResources(context).getDrawable(id)// ResourcesCompat.getDrawable()

    @JvmStatic
    fun getAssets(context: Context): AssetManager =
        getAppResources(context).assets

    @JvmStatic
    fun getResourceEntryName(context: Context, @AnyRes resId: Int): String =
        getAppResources(context).getResourceEntryName(resId)

    @JvmStatic
    fun getDimensionPixelOffset(context: Context, @DimenRes resId: Int): Int =
        getAppResources(context).getDimensionPixelOffset(resId)

    @JvmStatic
    fun getDimensionPixelSize(context: Context, @DimenRes resId: Int): Int =
        getAppResources(context).getDimensionPixelSize(resId)

    @JvmStatic
    fun getDimension(context: Context, @DimenRes resId: Int): Float =
        getAppResources(context).getDimension(resId)

    @JvmStatic
    fun getStringArray(context: Context, @ArrayRes resId: Int): Array<String> =
        getAppResources(context).getStringArray(resId)

    @JvmStatic
    fun getInteger(context: Context, @IntegerRes resId: Int): Int =
        getAppResources(context).getInteger(resId)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun obtainAttributes(context: Context, set: AttributeSet, @StyleableRes attrs: IntArray): TypedArray =
        getAppResources(context).obtainAttributes(set, attrs)
}