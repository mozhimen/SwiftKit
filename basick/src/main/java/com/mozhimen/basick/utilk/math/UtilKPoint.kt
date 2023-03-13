package com.mozhimen.basick.utilk.math

import kotlin.math.abs
import kotlin.math.sqrt


/**
 * @ClassName UtilKPoint
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 11:17
 * @Version 1.0
 */
object UtilKPoint {
    @JvmStatic
    fun center(ax: Float, ay: Float, bx: Float, by: Float): Pair<Float, Float> =
        Pair((ax + bx) / 2f, (ay + by) / 2f)

    @JvmStatic
    fun distance(ax: Float, ay: Float, bx: Float, by: Float): Float {
        val distance1 = abs(bx - ax)
        val distance2 = abs(by - ay)
        return sqrt(distance1 * distance1 + distance2 * distance2)
    }
}