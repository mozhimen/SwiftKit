package com.mozhimen.basick.utilk.android.util

import android.util.TypedValue
import androidx.annotation.FloatRange
import com.mozhimen.basick.elemk.android.util.cons.CDisplayMetrics
import com.mozhimen.basick.elemk.android.util.cons.CTypedValue

/**
 * @ClassName UtilKDisplayMetricsWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 10:27
 * @Version 1.0
 */
val Float.dp2px: Float
    get() = dp2px()

fun Float.dp2px(): Float =
    UtilKDisplayMetricsWrapper.dp2px(this)

val Int.dp2px: Float
    get() = dp2px()

fun Int.dp2px(): Float =
    UtilKDisplayMetricsWrapper.dp2px(this.toFloat())

val Float.dp2sp: Float
    get() = dp2sp()

fun Float.dp2sp(): Float =
    UtilKDisplayMetricsWrapper.dp2sp(this)

val Int.dp2sp: Float
    get() = dp2sp()

fun Int.dp2sp(): Float =
    UtilKDisplayMetricsWrapper.dp2sp(this.toFloat())

/////////////////////////////////////////////////////////////

val Float.px2dp: Float
    get() = px2dp()

fun Float.px2dp(): Float =
    UtilKDisplayMetricsWrapper.px2dp(this)

val Int.px2dp: Float
    get() = px2dp()

fun Int.px2dp(): Float =
    UtilKDisplayMetricsWrapper.px2dp(this.toFloat())

val Float.px2sp: Float
    get() = px2sp()

fun Float.px2sp(): Float =
    UtilKDisplayMetricsWrapper.px2sp(this)

val Int.px2sp: Float
    get() = px2sp()

fun Int.px2sp(): Float =
    UtilKDisplayMetricsWrapper.px2sp(this.toFloat())

/////////////////////////////////////////////////////////////

val Float.sp2px: Float
    get() = sp2px()

fun Float.sp2px(): Float =
    UtilKDisplayMetricsWrapper.sp2px(this)

val Int.sp2px: Float
    get() = sp2px()

fun Int.sp2px(): Float =
    UtilKDisplayMetricsWrapper.sp2px(this.toFloat())

object UtilKDisplayMetricsWrapper {

    @JvmStatic
    fun dp2px(@FloatRange(from = 0.0) dp: Float): Float =
        TypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_DIP, dp, UtilKDisplayMetrics.getSys())

    @JvmStatic
    fun dp2px2(@FloatRange(from = 0.0) dp: Float): Float =
        dp * UtilKDisplayMetrics.getSysDensity()

    @JvmStatic
    fun dp2px3(@FloatRange(from = 0.0) dp: Float): Float =
        dp * (UtilKDisplayMetrics.getSysXdpi() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun dp2sp(@FloatRange(from = 0.0) dp: Float): Float =
        dp2px(dp).px2sp()

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun px2dp(@FloatRange(from = 0.0) px: Float): Float =
        px / (UtilKDisplayMetrics.getSysDensityDpi() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun px2dp2(@FloatRange(from = 0.0) px: Float): Float =
        px / UtilKDisplayMetrics.getSysDensity()

    @JvmStatic
    fun px2sp(@FloatRange(from = 0.0) px: Float): Float =
        px / UtilKDisplayMetrics.getSysScaledDensity() + 0.5f

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun sp2px(@FloatRange(from = 0.0) sp: Float): Float =
        TypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_SP, sp, UtilKDisplayMetrics.getSys())

    @JvmStatic
    fun sp2px2(@FloatRange(from = 0.0) sp: Float): Float =
        sp * UtilKDisplayMetrics.getSysScaledDensity()
}