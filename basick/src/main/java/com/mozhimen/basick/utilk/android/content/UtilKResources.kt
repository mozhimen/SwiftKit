package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.annotation.AnimRes
import androidx.annotation.AnyRes
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
import androidx.annotation.XmlRes

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

    @JvmStatic
    fun getApp(context: Context): Resources =
        UtilKContext.getResources(context)

    ////////////////////////////////////////////////////////////////////////////

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
    fun getAppConfiguration(context: Context): Configuration =
        getConfiguration(getApp(context))

    @JvmStatic
    fun getAppDisplayMetrics(context: Context): DisplayMetrics =
        getDisplayMetrics(getApp(context))

    @JvmStatic
    fun getAppAssets(context: Context): AssetManager =
        getApp(context).assets

    @JvmStatic
    fun getAppResourceEntryName(context: Context, @AnyRes intResAny: Int): String =
        getApp(context).getResourceEntryName(intResAny)

    ////////////////////////////////////////////////////////////////////////////
    @JvmStatic
    fun getAppString(context: Context, @StringRes intResStr: Int): String =
        getApp(context).getString(intResStr)

    @JvmStatic
    fun getAppString(context: Context, @StringRes intResStr: Int, vararg formatArgs: Any): String =
        getApp(context).getString(intResStr, formatArgs)

    @JvmStatic
    fun getAppStringArray(context: Context, @ArrayRes intResArray: Int): Array<String> =
        getApp(context).getStringArray(intResArray)

    @JvmStatic
    fun getAppColor(context: Context, @ColorRes intResColor: Int): Int =
        getApp(context).getColor(intResColor)

    @JvmStatic
    @SuppressLint("UseCompatLoadingForDrawables")
    fun getAppDrawable(context: Context, @DrawableRes intResDrawable: Int): Drawable =
        getApp(context).getDrawable(intResDrawable)// ResourcesCompat.getDrawable()

    @JvmStatic
    fun getAppDimensionPixelOffset(context: Context, @DimenRes intResDimen: Int): Int =
        getApp(context).getDimensionPixelOffset(intResDimen)

    @JvmStatic
    fun getAppDimensionPixelSize(context: Context, @DimenRes intResDimen: Int): Int =
        getApp(context).getDimensionPixelSize(intResDimen)

    @JvmStatic
    fun getAppDimension(context: Context, @DimenRes intResDimen: Int): Float =
        getApp(context).getDimension(intResDimen)

    @JvmStatic
    fun getAppInteger(context: Context, @IntegerRes intResInt: Int): Int =
        getApp(context).getInteger(intResInt)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getAppLayout(context: Context, @LayoutRes intResLayout: Int): XmlResourceParser =
        getApp(context).getLayout(intResLayout)

    @JvmStatic
    fun getAppAnimation(context: Context, @AnimRes intResAnim: Int): XmlResourceParser =
        getApp(context).getAnimation(intResAnim)

    @JvmStatic
    fun getAppXml(context: Context, @BoolRes intResBool: Int): Boolean =
        getApp(context).getBoolean(intResBool)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getConfiguration(resources: Resources): Configuration =
        resources.configuration

    @JvmStatic
    fun getDisplayMetrics(resources: Resources): DisplayMetrics =
        resources.displayMetrics

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun obtainAttributes(context: Context, set: AttributeSet, @StyleableRes intArrayStyleable: IntArray): TypedArray =
        getApp(context).obtainAttributes(set, intArrayStyleable)

    @JvmStatic
    fun updateConfiguration(context: Context, configuration: Configuration, displayMetrics: DisplayMetrics) {
        getApp(context).updateConfiguration(configuration, displayMetrics)
    }
}