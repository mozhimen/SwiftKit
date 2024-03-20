package com.mozhimen.basick.utilk.kotlin.math

import kotlin.math.pow
import kotlin.math.sqrt


/**
 * @ClassName UtilKPoint
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 11:17
 * @Version 1.0
 */
object UtilKMathPoint {
    @JvmStatic
    fun center(x1: Float, y1: Float, x2: Float, y2: Float): Pair<Float, Float> =
        Pair((x1 + x2) / 2f, (y1 + y2) / 2f)

    @JvmStatic
    fun center(x1: Double, y1: Double, x2: Double, y2: Double): Pair<Double, Double> =
        Pair((x1 + x2) / 2.0, (y1 + y2) / 2.0)

    @JvmStatic
    fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float =
        sqrt((x1 - x2).pow(2f) + (y1 - y2).pow(2f))

    @JvmStatic
    fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double =
        sqrt((x1 - x2).pow(2.0) + (y1 - y2).pow(2.0))
}