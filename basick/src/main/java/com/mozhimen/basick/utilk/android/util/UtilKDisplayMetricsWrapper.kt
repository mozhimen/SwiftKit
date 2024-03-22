package com.mozhimen.basick.utilk.android.util

import android.content.Context
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
    UtilKDisplayMetricsWrapper.px2dp_ofSysDpi(this)

val Int.px2dp: Float
    get() = px2dp()

fun Int.px2dp(): Float =
    UtilKDisplayMetricsWrapper.px2dp_ofSysDpi(this.toFloat())

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
        TypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_DIP, dp, UtilKDisplayMetrics.get_ofSys())

    @JvmStatic
    fun dp2px_ofSys(@FloatRange(from = 0.0) dp: Float): Float =
        dp * UtilKDisplayMetrics.getDensity_ofSys()

    @JvmStatic
    fun dp2px_ofApp(context: Context, @FloatRange(from = 0.0) dp: Float): Float =
        dp * UtilKDisplayMetrics.getDensity_ofApp(context) + 0.5f

    @JvmStatic
    fun dp2px_ofDpi(@FloatRange(from = 0.0) dp: Float): Float =
        dp * (UtilKDisplayMetrics.getXdpi_ofSys() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun dp2sp(@FloatRange(from = 0.0) dp: Float): Float =
        dp2px(dp).px2sp()

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun px2dp_ofSysDpi(@FloatRange(from = 0.0) px: Float): Float =
        px / (UtilKDisplayMetrics.getDensityDpi_ofSys() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun px2dp_ofSys(@FloatRange(from = 0.0) px: Float): Float =
        px / UtilKDisplayMetrics.getDensity_ofSys()

    @JvmStatic
    fun px2sp(@FloatRange(from = 0.0) px: Float): Float =
        px / UtilKDisplayMetrics.getScaledDensity_ofSys() + 0.5f

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun sp2px(@FloatRange(from = 0.0) sp: Float): Float =
        TypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_SP, sp, UtilKDisplayMetrics.get_ofSys())

    @JvmStatic
    fun sp2px_ofSys(@FloatRange(from = 0.0) sp: Float): Float =
        sp * UtilKDisplayMetrics.getScaledDensity_ofSys()
}