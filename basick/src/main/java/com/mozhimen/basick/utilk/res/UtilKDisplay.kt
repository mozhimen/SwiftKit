package com.mozhimen.basick.utilk.res

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.content.activity.UtilKActivity
import com.mozhimen.basick.utilk.view.window.UtilKWindowManager

/**
 * @ClassName KDisplayUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:27
 * @Version 1.0
 */
val Float.dp2px: Float
    get() = dp2px()

fun Float.dp2px(): Float =
    UtilKDisplay.dp2px(this)

////////////////////////////////////

val Int.dp2px: Float
    get() = dp2px()

fun Int.dp2px(): Float =
    UtilKDisplay.dp2px(this.toFloat())

////////////////////////////////////

val Float.sp2px: Float
    get() = sp2px()

fun Float.sp2px(): Float =
    UtilKDisplay.sp2px(this)

////////////////////////////////////

val Int.sp2px: Float
    get() = sp2px()

fun Int.sp2px(): Float =
    UtilKDisplay.sp2px(this.toFloat())

////////////////////////////////////

val Int.px2dp: Float
    get() = px2dp()

fun Int.px2dp(): Float =
    UtilKDisplay.px2dp(this.toFloat())

////////////////////////////////////

val Float.px2dp: Float
    get() = px2dp()

fun Float.px2dp(): Float =
    UtilKDisplay.px2dp(this)

////////////////////////////////////

val Int.px2sp: Float
    get() = px2sp()

fun Int.px2sp(): Float =
    UtilKDisplay.px2sp(this.toFloat())

////////////////////////////////////

val Float.px2sp: Float
    get() = px2sp()

fun Float.px2sp(): Float =
    UtilKDisplay.px2sp(this)

object UtilKDisplay {

    /**
     * getDisplayMetrics
     * @return DisplayMetrics
     */
    @JvmStatic
    fun getDisplayMetrics(): DisplayMetrics {
        return UtilKResource.getDisplayMetrics()
    }

    /**
     * getDisplayMetrics
     * @return DisplayMetrics
     */
    @JvmStatic
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        UtilKWindowManager.getDefaultDisplay(context).getMetrics(displayMetrics)
        return displayMetrics
    }

    /**
     * 获取display
     * @param activity Activity
     * @return Display
     */
    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun getDisplay(activity: Activity): Display =
        UtilKActivity.getDisplay(activity)

    /**
     * 获取旋转
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_30_11_R)
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
