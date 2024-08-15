package com.mozhimen.basick.utilk.android.util

import android.content.Context
import android.util.DisplayMetrics
import com.mozhimen.basick.utilk.android.content.UtilKResources
import com.mozhimen.basick.utilk.android.view.UtilKDisplay
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * @ClassName UtilKDisplayMetrics
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 14:12
 * @Version 1.0
 */

object UtilKDisplayMetrics {

    //region # get function
    @JvmStatic
    fun get_ofApp(context: Context) =
        UtilKResources.getDisplayMetrics_ofApp(context)

    @JvmStatic
    fun get_ofSys(): DisplayMetrics =
        UtilKResources.getDisplayMetrics_ofSys()

    @JvmStatic
    fun get_ofDef(context: Context): DisplayMetrics =
        get_ofDef(context, DisplayMetrics())

    @JvmStatic
    fun get_ofDef(context: Context, displayMetrics: DisplayMetrics): DisplayMetrics {
        UtilKDisplay.getMetrics_ofDef(context, displayMetrics)
        return displayMetrics
    }

    @JvmStatic
    fun get_ofReal(context: Context): DisplayMetrics =
        get_ofReal(context, DisplayMetrics())

    @JvmStatic
    fun get_ofReal(context: Context, displayMetrics: DisplayMetrics): DisplayMetrics {
        UtilKDisplay.getRealMatrics_ofDef(context, displayMetrics)
        return displayMetrics
    }

    //////////////////////////////////////////////////////////////////////

    //region # app

    @JvmStatic
    fun getWidthPixels_ofApp(context: Context): Int =
        get_ofApp(context).widthPixels

    @JvmStatic
    fun getHeightPixels_ofApp(context: Context): Int =
        get_ofApp(context).heightPixels

    //获取密度
    @JvmStatic
    fun getDensity_ofApp(context: Context): Float =
        get_ofApp(context).density

    //endregion

    //////////////////////////////////////////////////////////////////////

    //region # sys
    //获取密度dp
    @JvmStatic
    fun getDensityDpi_ofSys(): Int =
        get_ofSys().densityDpi

    //获取密度
    @JvmStatic
    fun getDensity_ofSys(): Float =
        get_ofSys().density

    @JvmStatic
    fun getWidthPixels_ofSys(): Int =
        get_ofSys().widthPixels

    @JvmStatic
    fun getHeightPixels_ofSys(): Int =
        get_ofSys().heightPixels

    @JvmStatic
    fun getRatio_ofSys(): Float {
        val a = getWidthPixels_ofSys()
        val b = getHeightPixels_ofSys()
        val max = max(a, b).toFloat()
        val min = min(a, b).toFloat()
        return max / min
    }

    @JvmStatic
    fun getXdpi_ofSys(): Float =
        get_ofSys().xdpi

    @JvmStatic
    fun getYdpi_ofSys(): Float =
        get_ofSys().ydpi

    @JvmStatic
    fun getScaledDensity_ofSys(): Float =
        get_ofSys().scaledDensity

    //获取屏幕尺寸
    @JvmStatic
    fun getSize_ofSys(): Float {
        val xdpi = getXdpi_ofSys()
        val ydpi = getYdpi_ofSys()
        val width = getWidthPixels_ofSys()
        val height = getHeightPixels_ofSys()
        //计算屏幕的物理尺寸
        val widthPhy = (width / xdpi) * (width / xdpi)
        val heightPhy = (height / ydpi) * (height / ydpi)
        return sqrt(widthPhy + heightPhy)
    }

    fun isOrientationPortrait_ofSys(): Boolean =
        getHeightPixels_ofSys() >= getWidthPixels_ofSys()

    fun isOrientationLandscape_ofSys(): Boolean =
        !isOrientationPortrait_ofSys()

    fun getRelativeWidth_ofSys(isOrientationPortrait: Boolean): Int {
        val a = getWidthPixels_ofSys()
        val b = getHeightPixels_ofSys()
        val max = max(a, b)
        val min = min(a, b)
        val width = if (isOrientationPortrait) min
        else max
        return width
    }

    fun getRelativeHeight_ofSys(isOrientationPortrait: Boolean): Int {
        val a = getWidthPixels_ofSys()
        val b = getHeightPixels_ofSys()
        val max = max(a, b)
        val min = min(a, b)
        val height = if (isOrientationPortrait) max
        else min
        return height
    }
    //endregion

    //////////////////////////////////////////////////////////////////////

    //region # def
    @JvmStatic
    fun getWidthPixels_ofDef(context: Context): Int =
        get_ofDef(context).widthPixels

    @JvmStatic
    fun getHeightPixels_ofDef(context: Context): Int =
        get_ofDef(context).heightPixels

    @JvmStatic
    fun getRatio_ofDef(context: Context): Float {
        val max = max(getWidthPixels_ofDef(context), getHeightPixels_ofDef(context)).toFloat()
        val min = min(getWidthPixels_ofDef(context), getHeightPixels_ofDef(context)).toFloat()
        return max / min
    }
    //endregion

    //////////////////////////////////////////////////////////////////////

    //region # real
    @JvmStatic
    fun getWidthPixels_ofReal(context: Context): Int =
        get_ofReal(context).widthPixels

    @JvmStatic
    fun getHeightPixels_ofReal(context: Context): Int =
        get_ofReal(context).heightPixels

    @JvmStatic
    fun getRatio_ofReal(context: Context): Float {
        val max = max(getWidthPixels_ofReal(context), getHeightPixels_ofReal(context)).toFloat()
        val min = min(getWidthPixels_ofReal(context), getHeightPixels_ofReal(context)).toFloat()
        return max / min
    }
    //endregion
}