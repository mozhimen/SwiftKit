package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKRes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:56
 * @Version 1.0
 */
object UtilKRes : BaseUtilK() {

    @JvmStatic
    fun getString(@StringRes resId: Int, context: Context = _context): String =
        UtilKContext.getString(context, resId)

    @JvmStatic
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        UtilKContext.getString(_context, resId, formatArgs)

    @JvmStatic
    fun getStringArray(resId: Int): Array<String> =
        UtilKResources.getAppStringArray(_context, resId)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getColor(@ColorRes resId: Int): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) UtilKContext.getColor(_context, resId)
        else getColorOfCompat(resId)

    @JvmStatic
    fun getColorOfCompat(@ColorRes resId: Int): Int =
        UtilKContextCompat.getColor(_context, resId)

    @JvmStatic
    fun getColorStateList(@ColorRes resId: Int): ColorStateList? =
        if (UtilKBuildVersion.isAfterV_23_6_M()) UtilKContext.getColorStateList(_context, resId)
        else getColorStateListOfCompat(resId)

    @JvmStatic
    fun getColorStateListOfCompat(@ColorRes resId: Int): ColorStateList? =
        UtilKContextCompat.getColorStateList(_context, resId)

    /////////////////////////////////////////////////////////////////////

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawable(@DrawableRes resId: Int): Drawable? =
        UtilKContext.getDrawable(_context, resId)

    @JvmStatic
    fun getDrawableOfCompat(@DrawableRes resId: Int): Drawable? =
        UtilKContextCompat.getDrawable(_context, resId)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDimensionPixelOffset(@DimenRes resId: Int): Int =
        UtilKResources.getAppDimensionPixelOffset(_context, resId)

    @JvmStatic
    fun getDimensionPixelSize(@DimenRes resId: Int): Int =
        UtilKResources.getAppDimensionPixelSize(_context, resId)

    @JvmStatic
    fun getDimension(@DimenRes resId: Int): Float =
        UtilKResources.getAppDimension(_context, resId)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getInteger(@IntegerRes resId: Int): Int =
        UtilKResources.getAppInteger(_context, resId)
}