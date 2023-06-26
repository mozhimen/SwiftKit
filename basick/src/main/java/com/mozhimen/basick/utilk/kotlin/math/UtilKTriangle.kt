package com.mozhimen.basick.utilk.kotlin.math

import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin


/**
 * @ClassName UtilKTriangle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/10 16:55
 * @Version 1.0
 */
object UtilKTriangle {
    /**
     * 计算临边对斜边的角度
     * @param adjacent Double 临边
     * @param hypotenuse Double
     * @return Float
     */
    @JvmStatic
    fun angleCos(adjacent: Double, hypotenuse: Double): Double =
        Math.toDegrees(acos(adjacent / hypotenuse))

    /**
     * 计算对边对斜边的角度
     * @param opposite Double
     * @param hypotenuse Double
     * @return Float
     */
    @JvmStatic
    fun angleSin(opposite: Double, hypotenuse: Double): Double =
        Math.toDegrees(asin(opposite / hypotenuse))

    /**
     * 计算临边对斜边的角度
     * @param adjacent Float 临边
     * @param hypotenuse Float
     * @return Float
     */
    @JvmStatic
    fun angleCos(adjacent: Float, hypotenuse: Float): Float =
        Math.toDegrees(acos(adjacent / hypotenuse).toDouble()).toFloat()

    /**
     * 计算对边对斜边的角度
     * @param opposite Float
     * @param hypotenuse Float
     * @return Float
     */
    @JvmStatic
    fun angleSin(opposite: Float, hypotenuse: Float): Float =
        Math.toDegrees(asin(opposite / hypotenuse).toDouble()).toFloat()


    @JvmStatic
    fun getOppositeLength(hypotenuse: Double, angle: Double): Double {
        return sin(2.0 * Math.PI / 360.0 * angle) * hypotenuse
    }

    @JvmStatic
    fun getOppositeLength(hypotenuse: Float, angle: Float): Float {
        return sin((2.0 * Math.PI / 360.0 * angle).toFloat()) * hypotenuse
    }

    @JvmStatic
    fun getAdjacentLength(hypotenuse: Double, angle: Double): Double {
        return cos(2.0 * Math.PI / 360.0 * angle) * hypotenuse
    }

    @JvmStatic
    fun getAdjacentLength(hypotenuse: Float, angle: Float): Float {
        return cos((2.0 * Math.PI / 360.0 * angle).toFloat()) * hypotenuse
    }
}