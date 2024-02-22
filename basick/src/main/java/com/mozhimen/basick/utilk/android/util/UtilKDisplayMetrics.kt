package com.mozhimen.basick.utilk.android.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
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
    fun getApp(context: Context) =
        UtilKResources.getAppDisplayMetrics(context)

    @JvmStatic
    fun getSys(): DisplayMetrics =
        UtilKResources.getSysDisplayMetrics()

    @JvmStatic
    fun getDef(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        UtilKDisplay.getDefMetrics(context, displayMetrics)
        return displayMetrics
    }

    @JvmStatic
    fun getReal(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        Display::class.java.getMethod("getRealMetrics", DisplayMetrics::class.java).invoke(UtilKDisplay.getDef(context), displayMetrics)
        return displayMetrics;
    }

    //////////////////////////////////////////////////////////////////////

    //region # app

    @JvmStatic
    fun getAppWidthPixels(context: Context): Int =
        getApp(context).widthPixels

    @JvmStatic
    fun getAppHeightPixels(context: Context): Int =
        getApp(context).heightPixels

    /**
     * 获取密度
     */
    @JvmStatic
    fun getAppDensity(context: Context): Float =
        getApp(context).density
    //endregion

    //////////////////////////////////////////////////////////////////////

    //region # sys
    /**
     * 获取密度dp
     */
    @JvmStatic
    fun getSysDensityDpi(): Int =
        getSys().densityDpi

    /**
     * 获取密度
     */
    @JvmStatic
    fun getSysDensity(): Float =
        getSys().density

    @JvmStatic
    fun getSysWidthPixels(): Int =
        getSys().widthPixels

    @JvmStatic
    fun getSysHeightPixels(): Int =
        getSys().heightPixels

    @JvmStatic
    fun getSysRatio(): Float {
        val max = max(getSysWidthPixels(), getSysHeightPixels()).toFloat()
        val min = min(getSysWidthPixels(), getSysHeightPixels()).toFloat()
        return max / min
    }

    @JvmStatic
    fun getSysXdpi(): Float =
        getSys().xdpi

    @JvmStatic
    fun getSysYdpi(): Float =
        getSys().ydpi

    @JvmStatic
    fun getSysScaledDensity(): Float =
        getSys().scaledDensity

    /**
     * 获取屏幕尺寸
     */
    @JvmStatic
    fun getSysSize(): Float {
        val xdpi = getSysXdpi()
        val ydpi = getSysYdpi()
        val width = getSysWidthPixels()
        val height = getSysHeightPixels()
        //计算屏幕的物理尺寸
        val widthPhy = (width / xdpi) * (width / xdpi)
        val heightPhy = (height / ydpi) * (height / ydpi)
        return sqrt(widthPhy + heightPhy)
    }

    fun isSysOrientationPortrait(): Boolean =
        getSysHeightPixels() >= getSysWidthPixels()

    fun isSysOrientationLandscape(): Boolean =
        !isSysOrientationPortrait()
    //endregion

    //////////////////////////////////////////////////////////////////////

    //region # def
    @JvmStatic
    fun getDefWidthPixels(context: Context): Int =
        getDef(context).widthPixels

    @JvmStatic
    fun getDefHeightPixels(context: Context): Int =
        getDef(context).heightPixels

    @JvmStatic
    fun getDefRatio(context: Context): Float {
        val max = max(getDefWidthPixels(context), getDefHeightPixels(context)).toFloat()
        val min = min(getDefWidthPixels(context), getDefHeightPixels(context)).toFloat()
        return max / min
    }
    //endregion

    //////////////////////////////////////////////////////////////////////

    //region # real
    @JvmStatic
    fun getRealWidthPixels(context: Context): Int =
        getReal(context).widthPixels

    @JvmStatic
    fun getRealHeightPixels(context: Context): Int =
        getReal(context).heightPixels

    @JvmStatic
    fun getRealRatio(context: Context): Float {
        val max = max(getRealWidthPixels(context), getRealHeightPixels(context)).toFloat()
        val min = min(getRealWidthPixels(context), getRealHeightPixels(context)).toFloat()
        return max / min
    }
    //endregion
}