package com.mozhimen.basick.utilk.res

import android.app.Activity
import android.content.Context
import android.hardware.display.DisplayManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.content.activity.UtilKActivity
import com.mozhimen.basick.utilk.view.window.UtilKWindowManager

/**
 * @ClassName KDisplayUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:27
 * @Version 1.0
 */
object UtilKDisplay {
    private val _displayMetrics = UtilKRes.getSystemResources().displayMetrics

    @JvmStatic
    fun getDisplayManager(context: Context) : DisplayManager =
        UtilKContext.getDisplayManager(context)

    /**
     * getDisplayMetrics
     * @return DisplayMetrics
     */
    @JvmStatic
    fun getDisplayMetrics(): DisplayMetrics {
        return _displayMetrics
    }

    /**
     * getDisplayMetrics
     * @return DisplayMetrics
     */
    @JvmStatic
    fun getDisplayMetrics2(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        UtilKWindowManager.getDefaultDisplay().getMetrics(displayMetrics)
        return displayMetrics
    }

    /**
     * 获取display
     * @param activity Activity
     * @return Display
     */
    @JvmStatic
    fun getDisplay(activity: Activity): Display =
        UtilKActivity.getDisplay(activity)

    /**
     * 获取旋转
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getRotation(activity: Activity): Int =
        getDisplay(activity).rotation

    /**
     * dp转px
     * @param dp Float
     * @return Int
     */
    @JvmStatic
    fun dp2px(dp: Float): Float =
        if (dp <= 0f) 0f
        else dp * (getXdpi() / DisplayMetrics.DENSITY_DEFAULT)

    /**
     * dp转px
     * @param dp Float
     * @return Int
     */
    @JvmStatic
    fun dp2px2(dp: Float): Float =
        if (dp <= 0f) 0f
        else dp * getDensity()

    /**
     * dp转px
     * @param dp Float
     * @return Float
     */
    @JvmStatic
    fun dp2px3(dp: Float): Float =
        if (dp <= 0f) 0f
        else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplayMetrics())

    /**
     * px转dp
     * @param px Int
     * @return Float
     */
    @JvmStatic
    fun px2dp(px: Float): Float =
        if (px <= 0f) 0f
        else px / (getDensityDpi() / DisplayMetrics.DENSITY_DEFAULT)

    /**
     * px转dp
     * @param px Float
     * @return Int
     */
    @JvmStatic
    fun px2dp2(px: Float): Float =
        if (px <= 0f) 0f
        else px / getDensity()

    /**
     * px转sp
     * @param px Float
     * @return Float
     */
    @JvmStatic
    fun px2sp(px: Float): Float =
        if (px <= 0f) 0f
        else px / getScaledDensity() + 0.5f

    /**
     * sp转px
     * @param sp Float
     * @return Int
     */
    @JvmStatic
    fun sp2px(sp: Float): Float =
        if (sp <= 0f) 0f
        else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getDisplayMetrics())

    /**
     * 获取密度dp
     * @return Int
     */
    @JvmStatic
    fun getDensityDpi(): Int =
        getDisplayMetrics().densityDpi

    /**
     * 获取密度
     * @return Float
     */
    @JvmStatic
    fun getDensity(): Float =
        getDisplayMetrics().density

    /**
     * 获取widthPixels
     * @return Int
     */
    @JvmStatic
    fun getWidthPixels(): Int =
        getDisplayMetrics().widthPixels

    /**
     * 获取heightPixels
     * @return Int
     */
    @JvmStatic
    fun getHeightPixels(): Int =
        getDisplayMetrics().heightPixels

    /**
     * 获取xdpi
     * @return Float
     */
    @JvmStatic
    fun getXdpi(): Float =
        getDisplayMetrics().xdpi

    /**
     * 获取ydpi
     * @return Float
     */
    @JvmStatic
    fun getYdpi(): Float =
        getDisplayMetrics().ydpi

    /**
     * 获取ScaledDensity
     * @return Float
     */
    @JvmStatic
    fun getScaledDensity(): Float =
        getDisplayMetrics().scaledDensity
}
