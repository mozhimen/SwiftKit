package com.mozhimen.basick.utilk.kotlin.math

/**
 * @ClassName UtilKMathInterpolation
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKMathInterpolation {
    @JvmStatic
    fun get_ofLinear(t: Float, a: Float, b: Float): Float =
        (a * (1.0f - t)) + (b * t)
}