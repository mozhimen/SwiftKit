package com.mozhimen.basick.utilk.kotlin.math

import android.graphics.PointF
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * @ClassName UtilKMathCoordinates
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKMathCoordinates {
    @JvmStatic
    fun coordinatesPolar2square(angle: Float, strength: Float): PointF {
        val u = strength * cos(angle)
        val v = strength * sin(angle)
        return coordinatesEllipticalDisk2square(u, v)
    }

    @JvmStatic
    fun coordinatesEllipticalDisk2square(u: Float, v: Float): PointF {
        val u2 = u * u
        val v2 = v * v
        val twoSqrt2 = 2.0f * sqrt(2.0f)
        val subTermX = 2.0f + u2 - v2
        val subTermY = 2.0f - u2 + v2
        val termX1 = subTermX + u * twoSqrt2
        val termX2 = subTermX - u * twoSqrt2
        val termY1 = subTermY + v * twoSqrt2
        val termY2 = subTermY - v * twoSqrt2

        val x = (0.5f * sqrt(termX1) - 0.5f * sqrt(termX2))
        val y = (0.5f * sqrt(termY1) - 0.5f * sqrt(termY2))

        return PointF(x, y)
    }
}