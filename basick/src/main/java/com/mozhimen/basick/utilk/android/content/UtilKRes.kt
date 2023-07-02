package com.mozhimen.basick.utilk.android.content

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mozhimen.basick.elemk.cons.CVersCode
import com.mozhimen.basick.utilk.android.os.UtilKBuildVers
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
    fun getString(@StringRes resId: Int): String =
        UtilKContext.getString(_context, resId)

    @JvmStatic
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        UtilKContext.getString(_context, resId, formatArgs)

    @JvmStatic
    fun getStringArray(resId: Int): Array<String> =
        UtilKResource.getStringArray(resId, _context)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getColor(@ColorRes resId: Int): Int =
        if (UtilKBuildVers.isAfterV_23_6_M()) UtilKContext.getColor(_context, resId)
        else getColor2(resId)

    @JvmStatic
    fun getColor2(@ColorRes resId: Int): Int =
        UtilKContextCompat.getColor(_context, resId)

    @JvmStatic
    fun getColorStateList(@ColorRes resId: Int): ColorStateList? =
        if (UtilKBuildVers.isAfterV_23_6_M()) UtilKContext.getColorStateList(_context, resId)
        else getColorStateList2(resId)

    @JvmStatic
    fun getColorStateList2(@ColorRes resId: Int): ColorStateList? =
        UtilKContextCompat.getColorStateList(_context, resId)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDrawable(@DrawableRes drawableId: Int): Drawable? =
        UtilKContext.getDrawable(_context, drawableId)

    @JvmStatic
    fun getDrawable2(@DrawableRes drawableId: Int): Drawable? =
        UtilKContextCompat.getDrawable(_context, drawableId)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDimensionPixelOffset(dimensionId: Int): Int =
        UtilKResource.getDimensionPixelOffset(dimensionId, _context)

    @JvmStatic
    fun getDimensionPixelSize(dimensionId: Int): Int =
        UtilKResource.getDimensionPixelSize(dimensionId, _context)

    @JvmStatic
    fun getDimension(dimensionId: Int): Float =
        UtilKResource.getDimension(dimensionId, _context)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getInteger(resId: Int): Int =
        UtilKResource.getInteger(resId, _context)
}