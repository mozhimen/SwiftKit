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
object UtilKBall {

    /**
     * 距离
     */
    @JvmStatic
    fun distance(longitude1: Double, latitude1: Double, longitude2: Double, latitude2: Double): Double {
        var dist = (sin(angle2radian(latitude1)) * sin(angle2radian(latitude2)) + (cos(angle2radian(latitude1)) * cos(angle2radian(latitude2)) * cos(angle2radian(longitude1 - longitude2))))
        dist = acos(dist)
        dist = radian2angle(dist)
        return dist * 60 * 1.1515
    }

    /**
     * 将角度转换为弧度
     */
    @JvmStatic
    fun angle2radian(degree: Double): Double =
            degree / 180 * Math.PI

    /**
     * 将弧度转换为角度
     */
    @JvmStatic
    fun radian2angle(radian: Double): Double =
            radian * 180 / Math.PI
}