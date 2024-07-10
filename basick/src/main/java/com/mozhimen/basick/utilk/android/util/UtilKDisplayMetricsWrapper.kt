package com.mozhimen.basick.utilk.android.util

import android.content.Context
import android.util.DisplayMetrics
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
fun Float.dp2px(): Float =
    UtilKDisplayMetricsWrapper.dp2px(this)

fun Int.dp2px(): Float =
    UtilKDisplayMetricsWrapper.dp2px(this.toFloat())

fun Float.dp2pxI(): Int =
    UtilKDisplayMetricsWrapper.dp2pxI(this)

fun Int.dp2pxI(): Int =
    UtilKDisplayMetricsWrapper.dp2pxI(this.toFloat())

/////////////////////////////////////////////////////////////

fun Float.dp2sp(): Float =
    UtilKDisplayMetricsWrapper.dp2sp(this)

fun Int.dp2sp(): Float =
    UtilKDisplayMetricsWrapper.dp2sp(this.toFloat())

/////////////////////////////////////////////////////////////

fun Float.px2dp(): Float =
    UtilKDisplayMetricsWrapper.px2dp_ofSysDpi(this)

fun Int.px2dp(): Float =
    UtilKDisplayMetricsWrapper.px2dp_ofSysDpi(this.toFloat())

/////////////////////////////////////////////////////////////

fun Float.px2sp(): Float =
    UtilKDisplayMetricsWrapper.px2sp(this)

fun Int.px2sp(): Float =
    UtilKDisplayMetricsWrapper.px2sp(this.toFloat())

/////////////////////////////////////////////////////////////

fun Float.sp2px(): Float =
    UtilKDisplayMetricsWrapper.sp2px(this)

fun Int.sp2px(): Float =
    UtilKDisplayMetricsWrapper.sp2px(this.toFloat())

fun Float.sp2pxI(): Int =
    UtilKDisplayMetricsWrapper.sp2pxI(this)

fun Int.sp2pxI(): Int =
    UtilKDisplayMetricsWrapper.sp2pxI(this.toFloat())

/////////////////////////////////////////////////////////////

object UtilKDisplayMetricsWrapper {

    //region # dp -> px
    @JvmStatic
    fun dp2px(@FloatRange(from = 0.0) dp: Float): Float =
        dp2px_ofSys(dp)

    @JvmStatic
    fun dp2pxI(@FloatRange(from = 0.0) dp: Float): Int =
        (dp2px(dp) + 0.5f).toInt()

    @JvmStatic
    fun dp2px_ofSys(@FloatRange(from = 0.0) dp: Float): Float =
        dp2px(dp, UtilKDisplayMetrics.get_ofSys())

    @JvmStatic
    fun dp2px_ofSysDpi(@FloatRange(from = 0.0) dp: Float): Float =
        dp * (UtilKDisplayMetrics.getXdpi_ofSys() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun dp2px_ofApp(@FloatRange(from = 0.0) dp: Float, context: Context): Float =
        dp2px(dp, UtilKDisplayMetrics.get_ofApp(context))

    @JvmStatic
    fun dp2px(@FloatRange(from = 0.0) dp: Float, displayMetrics: DisplayMetrics): Float =
        UtilKTypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
    //endregion

    /////////////////////////////////////////////////////////////

    //region # dp -> sp
    @JvmStatic
    fun dp2sp(@FloatRange(from = 0.0) dp: Float): Float =
        dp2px(dp).px2sp()
    //endregion

    /////////////////////////////////////////////////////////////

    //region # px -> dp
    @JvmStatic
    fun px2dp_ofSysDpi(@FloatRange(from = 0.0) px: Float): Float =
        px / (UtilKDisplayMetrics.getDensityDpi_ofSys() / CDisplayMetrics.DENSITY_DEFAULT)

    @JvmStatic
    fun px2dp_ofSys(@FloatRange(from = 0.0) px: Float): Float =
        px / UtilKDisplayMetrics.getDensity_ofSys()
    //endregion

    /////////////////////////////////////////////////////////////

    //region # px -> sp
    @JvmStatic
    fun px2sp(@FloatRange(from = 0.0) px: Float): Float =
        px / UtilKDisplayMetrics.getScaledDensity_ofSys() /*+ 0.5f*/
    //endregion

    /////////////////////////////////////////////////////////////

    //region # sp -> px
    @JvmStatic
    fun sp2px(@FloatRange(from = 0.0) sp: Float): Float =
        sp2px_ofSys(sp)

    @JvmStatic
    fun sp2pxI(@FloatRange(from = 0.0) sp: Float): Int =
        (sp2px(sp) + 0.5f).toInt()

    @JvmStatic
    fun sp2px_ofSys(@FloatRange(from = 0.0) sp: Float): Float =
        sp2px(sp, UtilKDisplayMetrics.get_ofSys())

    @JvmStatic
    fun sp2px_ofApp(@FloatRange(from = 0.0) sp: Float, context: Context): Float =
        sp2px(sp, UtilKDisplayMetrics.get_ofApp(context))

    @JvmStatic
    fun sp2px(@FloatRange(from = 0.0) sp: Float, displayMetrics: DisplayMetrics): Float =
        UtilKTypedValue.applyDimension(CTypedValue.COMPLEX_UNIT_SP, sp, displayMetrics)
    //endregion
}