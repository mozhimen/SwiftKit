package com.mozhimen.basick.utilk.kotlin.math

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

/**
 * @ClassName UtilKBall
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/23 20:54
 * @Version 1.0
 */
object UtilKMathBall {

    //距离
    @JvmStatic
    fun getDistance(longitude1: Double, latitude1: Double, longitude2: Double, latitude2: Double): Double {
        var dist = (sin(numAngle2numRadian(latitude1)) * sin(numAngle2numRadian(latitude2)) + (cos(numAngle2numRadian(latitude1)) * cos(numAngle2numRadian(latitude2)) * cos(numAngle2numRadian(longitude1 - longitude2))))
        dist = acos(dist)
        dist = numRadian2numAngle(dist)
        return dist * 60 * 1.1515
    }

    ///////////////////////////////////////////////////////////////////////////////////

    //将角度转换为弧度
    @JvmStatic
    fun numAngle2numRadian(degree: Double): Double =
            degree / 180.0 * Math.PI

    //将弧度转换为角度
    @JvmStatic
    fun numRadian2numAngle(radian: Double): Double =
            radian * 180.0 / Math.PI
}