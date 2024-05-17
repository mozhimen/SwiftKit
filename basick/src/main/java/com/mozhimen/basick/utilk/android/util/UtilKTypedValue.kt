package com.mozhimen.basick.utilk.android.util

import android.content.Context
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

    @JvmStatic
    fun complexToFloat(context: Context, intResId: Int): Float {
        val typedValue = TypedValue()
        context.resources.getValue(intResId, typedValue, true)
        return complexToFloat(typedValue.data)
    }

    @JvmStatic
    fun complexToFloat(complex: Int): Float =
        TypedValue.complexToFloat(complex)
}