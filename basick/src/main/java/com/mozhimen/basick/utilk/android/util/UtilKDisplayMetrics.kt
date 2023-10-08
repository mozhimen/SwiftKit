package com.mozhimen.basick.utilk.android.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.FloatRange
import com.mozhimen.basick.elemk.android.util.cons.CDisplayMetrics
import com.mozhimen.basick.elemk.android.util.cons.CTypedValue
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.basick.utilk.android.view.UtilKDisplay

/**
 * @ClassName UtilKDisplayMetrics
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 14:12
 * @Version 1.0
 */
val Float.dp2px: Float
    get() = dp2px()

fun Float.dp2px(): Float =
    UtilKDisplayMetrics.dp2px(this)

val Int.dp2px: Float
    get() = dp2px()

fun Int.dp2px(): Float =
    UtilKDisplayMetrics.dp2px(this.toFloat())

/////////////////////////////////////////////////////////////

val Float.px2dp: Float
    get() = px2dp()

fun Float.px2dp(): Float =
    UtilKDisplayMetrics.px2dp(this)

val Int.px2dp: Float
    get() = px2dp()

fun Int.px2dp(): Float =
    UtilKDisplayMetrics.px2dp(this.toFloat())

/////////////////////////////////////////////////////////////

val Float.px2sp: Float
    get() = px2sp()

fun Float.px2sp(): Float =
    UtilKDisplayMetrics.px2sp(this)

val Int.px2sp: Float
    get() = px2sp()

fun Int.px2sp(): Float =
    UtilKDisplayMetrics.px2sp(this.toFloat())

/////////////////////////////////////////////////////////////

val Float.sp2px: Float
    get() = sp2px()

fun Float.sp2px(): Float =
    UtilKDisplayMetrics.sp2px(this)

val Int.sp2px: Float
    get() = sp2px()

fun Int.sp2px(): Float =
    UtilKDisplayMetrics.sp2px(this.toFloat())

object UtilKDisplayMetrics {

    //region # get function
    @JvmStatic
    fun get(): DisplayMetrics =
        UtilKResource.getDisplayMetrics()

    @JvmStatic
    fun get(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        UtilKDisplay.getDefaultMetrics(context, displayMetrics)
        return displayMetrics
    }

    /**
     * 获取密度dp
     * @return Int
     */
    @JvmStatic
    fun getDensityDpi(): Int =
        get().densityDpi

    /**
     * 获取密度
     * @return Float
     */
    @JvmStatic
    fun getDensity(): Float =
        get().density

    /**
     * 获取widthPixels
     * @return Int
     */
    @JvmStatic
    fun getWidthPixels(): Int =
        get().widthPixels

    /**
     * 获取heightPixels
     * @return Int
     */
    @JvmStatic
    fun getHeightPixels(): Int =
        get().heightPixels

    @JvmStatic
    fun getXdpi(): Float =
        get().xdpi

    @JvmStatic
    fun getYdpi(): Float =
        get().ydpi

    @JvmStatic
    fun getScaledDensity(): Float =
        get().scaledDensity
    //endregion

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun dp2px(@FloatRange(from = 0.0) dp: Float): Float =
        dp * (getXdpi() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun dp2px2(@FloatRange(from = 0.0) dp: Float): Float =
        dp * getDensity()

    @JvmStatic
    fun dp2px3(@FloatRange(from = 0.0) dp: Float): Float =
        TypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_DIP, dp, get())

    @JvmStatic
    fun px2dp(@FloatRange(from = 0.0) px: Float): Float =
        px / (getDensityDpi() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun px2dp2(@FloatRange(from = 0.0) px: Float): Float =
        px / getDensity()

    @JvmStatic
    fun px2sp(@FloatRange(from = 0.0) px: Float): Float =
        px / getScaledDensity() + 0.5f

    @JvmStatic
    fun sp2px(@FloatRange(from = 0.0) sp: Float): Float =
        TypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_SP, sp, get())

    @JvmStatic
    fun sp2px2(@FloatRange(from = 0.0) sp: Float): Float =
        sp * getScaledDensity()
}