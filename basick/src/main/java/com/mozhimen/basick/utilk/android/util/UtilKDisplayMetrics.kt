package com.mozhimen.basick.utilk.android.util

import android.content.Context
import android.util.DisplayMetrics
import com.mozhimen.basick.utilk.android.content.UtilKResources
import com.mozhimen.basick.utilk.android.view.UtilKDisplay
import com.mozhimen.basick.utilk.android.view.UtilKScreen
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

    //////////////////////////////////////////////////////////////////////

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

    /**
     * 获取widthPixels
     */
    @JvmStatic
    fun getSysWidthPixels(): Int =
        getSys().widthPixels

    /**
     * 获取heightPixels
     */
    @JvmStatic
    fun getSysHeightPixels(): Int =
        getSys().heightPixels

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

    //////////////////////////////////////////////////////////////////////


    @JvmStatic
    fun getDefWidthPixels(context: Context): Int =
        getDef(context).widthPixels

    @JvmStatic
    fun getDefHeightPixels(context: Context): Int =
        getDef(context).heightPixels

}