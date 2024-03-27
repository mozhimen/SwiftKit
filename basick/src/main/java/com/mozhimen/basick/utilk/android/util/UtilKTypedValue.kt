package com.mozhimen.basick.utilk.android.util

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.core.util.TypedValueCompat

/**
 * @ClassName UtilKTypedValue
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/27
 * @Version 1.0
 */
object UtilKTypedValue {
    @JvmStatic
    fun applyDimension(@TypedValueCompat.ComplexDimensionUnit unit: Int, value: Float, metrics: DisplayMetrics): Float =
        TypedValue.applyDimension(unit, value, metrics)
}