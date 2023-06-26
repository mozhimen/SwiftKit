package com.mozhimen.basick.utilk.kotlin.math

import com.mozhimen.basick.elemk.mos.MPointF
import kotlin.math.abs
import kotlin.math.sqrt


/**
 * @ClassName UtilKQuadrant
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 11:12
 * @Version 1.0
 */
object UtilKQuadrant {

    /**
     * 计算临边对斜边的角度在象限中
     * @param point MPoint
     * @return Float
     */
    @JvmStatic
    fun angleCosInQuadrant(point: MPointF, centerPoint: MPointF): Float {
        return when {
            point.x == centerPoint.x && point.y >= centerPoint.y -> {
                0f
            }
            point.y == centerPoint.y && point.x >= centerPoint.x -> {
                90f
            }
            point.x == centerPoint.x && point.y < centerPoint.y -> {
                180f
            }
            point.y == centerPoint.y && point.x < centerPoint.x -> {
                270f
            }
            point.x > centerPoint.x && point.y > centerPoint.y -> {
                UtilKTriangle.angleCos(abs(point.y - centerPoint.y), sqrt(abs(point.x - centerPoint.x) * abs(point.x - centerPoint.x) + abs(point.y - centerPoint.y) * abs(point.y - centerPoint.y)))
            }
            point.x > centerPoint.x && point.y < centerPoint.y -> {
                90f + UtilKTriangle.angleCos(
                    abs(point.x - centerPoint.x),
                    sqrt(abs(point.x - centerPoint.x) * abs(point.x - centerPoint.x) + abs(point.y - centerPoint.y) * abs(point.y - centerPoint.y))
                )
            }
            point.x < centerPoint.x && point.y < centerPoint.y -> {
                180f + UtilKTriangle.angleCos(
                    abs(point.y - centerPoint.y),
                    sqrt(abs(point.x - centerPoint.x) * abs(point.x - centerPoint.x) + abs(point.y - centerPoint.y) * abs(point.y - centerPoint.y))
                )
            }
            point.x < centerPoint.x && point.y > centerPoint.y -> {
                270f + UtilKTriangle.angleCos(
                    abs(point.x - centerPoint.x),
                    sqrt(abs(point.x - centerPoint.x) * abs(point.x - centerPoint.x) + abs(point.y - centerPoint.y) * abs(point.y - centerPoint.y))
                )
            }
            else -> {
                0f
            }
        }
    }

    /**
     * 计算临边对斜边的角度在象限中
     * @param point MPoint
     * @return Float
     */
    @JvmStatic
    fun angleCosInQuadrant(point: MPointF): Float {
        return when {
            point.x == 0f && point.y >= 0f -> {
                0f
            }
            point.y == 0f && point.x >= 0f -> {
                90f
            }
            point.x == 0f && point.y < 0f -> {
                180f
            }
            point.y == 0f && point.x < 0f -> {
                270f
            }
            point.x > 0f && point.y > 0f -> {
                UtilKTriangle.angleCos(abs(point.y), sqrt(abs(point.x) * abs(point.x) + abs(point.y) * abs(point.y)))
            }
            point.x > 0f && point.y < 0f -> {
                90f + UtilKTriangle.angleCos(abs(point.x), sqrt(abs(point.x) * abs(point.x) + abs(point.y) * abs(point.y)))
            }
            point.x < 0f && point.y < 0f -> {
                180f + UtilKTriangle.angleCos(abs(point.y), sqrt(abs(point.x) * abs(point.x) + abs(point.y) * abs(point.y)))
            }
            point.x < 0f && point.y > 0f -> {
                270f + UtilKTriangle.angleCos(abs(point.x), sqrt(abs(point.x) * abs(point.x) + abs(point.y) * abs(point.y)))
            }
            else -> {
                0f
            }
        }
    }
}