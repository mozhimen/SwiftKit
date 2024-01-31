package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.androidx.core.UtilKResourcesCompat
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKRes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:56
 * @Version 1.0
 */
fun Context.gainColor(@ColorRes intResColor: Int): Int =
    UtilKContextWrapper.gainColor(this, intResColor)

///////////////////////////////////////////////////////////////////////////////////

object UtilKRes : BaseUtilK() {

    @JvmStatic
    fun getStringOfContext(context: Context, @StringRes intResStr: Int): String =
        UtilKContext.getString(context, intResStr)

    @JvmStatic
    fun getStringOfContext(@StringRes intResStr: Int): String =
        getStringOfContext(_context, intResStr)

    @JvmStatic
    fun getStringOfResources(context: Context, @StringRes intResStr: Int): String =
        UtilKResources.getAppString(context, intResStr)

    @JvmStatic
    fun getStringOfResources(@StringRes intResStr: Int): String =
        getStringOfResources(_context, intResStr)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStringOfContext(context: Context, @StringRes intResStr: Int, vararg formatArgs: Any): String =
        UtilKContext.getString(context, intResStr, formatArgs)

    @JvmStatic
    fun getStringOfContext(@StringRes intResStr: Int, vararg formatArgs: Any): String =
        getStringOfContext(_context, intResStr, formatArgs)

    @JvmStatic
    fun getStringOfResources(context: Context, @StringRes intResStr: Int, vararg formatArgs: Any): String =
        UtilKContext.getString(context, intResStr, formatArgs)

    @JvmStatic
    fun getStringOfResources(@StringRes intResStr: Int, vararg formatArgs: Any): String =
        getStringOfResources(_context, intResStr, formatArgs)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStringArrayOfResources(context: Context, @ArrayRes intResArray: Int): Array<String> =
        UtilKResources.getAppStringArray(context, intResArray)

    @JvmStatic
    fun getStringArrayOfResources(@ArrayRes intResArray: Int): Array<String> =
        getStringArrayOfResources(_context, intResArray)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainColor(context: Context, @ColorRes intResColor: Int): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) getColorOfContext(context, intResColor)
        else getColorOfContextCompat(intResColor)

    @JvmStatic
    fun gainColor(@ColorRes intResColor: Int): Int =
        gainColor(_context, intResColor)

    //////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getColorOfContext(context: Context, @ColorRes intResColor: Int): Int =
        UtilKContext.getColor(context, intResColor)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getColorOfContext(@ColorRes intResColor: Int): Int =
        getColorOfContext(_context, intResColor)

    @JvmStatic
    fun getColorOfResources(context: Context, @ColorRes intResColor: Int): Int =
        UtilKResources.getAppColor(context, intResColor)

    @JvmStatic
    fun getColorOfResources(@ColorRes intResColor: Int): Int =
        getColorOfResources(_context, intResColor)

    @JvmStatic
    fun getColorOfContextCompat(context: Context, @ColorRes intResColor: Int): Int =
        UtilKContextCompat.getColor(context, intResColor)

    @JvmStatic
    fun getColorOfContextCompat(@ColorRes intResColor: Int): Int =
        getColorOfContextCompat(_context, intResColor)

    @JvmStatic
    fun getColorOfResourcesCompat(context: Context, @ColorRes intResColor: Int, theme: Theme?): Int =
        UtilKResourcesCompat.getColor(UtilKResources.getApp(context), intResColor, theme)

    @JvmStatic
    fun getColorOfResourcesCompat(@ColorRes intResColor: Int, theme: Theme?): Int =
        getColorOfResourcesCompat(_context, intResColor, theme)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getColorStateList(@ColorRes intResColor: Int): ColorStateList? =
        if (UtilKBuildVersion.isAfterV_23_6_M()) UtilKContext.getColorStateList(_context, intResColor)
        else getColorStateListOfCompat(intResColor)

    @JvmStatic
    fun getColorStateListOfCompat(@ColorRes intResColor: Int): ColorStateList? =
        UtilKContextCompat.getColorStateList(_context, intResColor)

    /////////////////////////////////////////////////////////////////////

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawable(@DrawableRes intResDrawable: Int): Drawable? =
        UtilKContext.getDrawable(_context, intResDrawable)

    @JvmStatic
    fun getDrawableOfCompat(@DrawableRes intResDrawable: Int): Drawable? =
        UtilKContextCompat.getDrawable(_context, intResDrawable)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDimensionPixelOffset(@DimenRes intResDimen: Int): Int =
        UtilKResources.getAppDimensionPixelOffset(_context, intResDimen)

    @JvmStatic
    fun getDimensionPixelSize(@DimenRes intResDimen: Int): Int =
        UtilKResources.getAppDimensionPixelSize(_context, intResDimen)

    @JvmStatic
    fun getDimension(@DimenRes intResDimen: Int): Float =
        UtilKResources.getAppDimension(_context, intResDimen)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getInteger(@IntegerRes intResInt: Int): Int =
        UtilKResources.getAppInteger(_context, intResInt)
}