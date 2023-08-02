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
     * @param pointF MPoint
     * @return Float
     */
    @JvmStatic
    fun angleCosInQuadrant(pointF: MPointF, centerPointF: MPointF): Float {
        return when {
            pointF.x == centerPointF.x && pointF.y >= centerPointF.y -> 0f

            pointF.y == centerPointF.y && pointF.x >= centerPointF.x -> 90f

            pointF.x == centerPointF.x && pointF.y < centerPointF.y -> 180f

            pointF.y == centerPointF.y && pointF.x < centerPointF.x -> 270f

            pointF.x > centerPointF.x && pointF.y > centerPointF.y ->
                UtilKTriangle.angleCos(abs(pointF.y - centerPointF.y), sqrt(abs(pointF.x - centerPointF.x) * abs(pointF.x - centerPointF.x) + abs(pointF.y - centerPointF.y) * abs(pointF.y - centerPointF.y)))

            pointF.x > centerPointF.x && pointF.y < centerPointF.y ->
                90f + UtilKTriangle.angleCos(
                        abs(pointF.x - centerPointF.x),
                        sqrt(abs(pointF.x - centerPointF.x) * abs(pointF.x - centerPointF.x) + abs(pointF.y - centerPointF.y) * abs(pointF.y - centerPointF.y))
                )

            pointF.x < centerPointF.x && pointF.y < centerPointF.y ->
                180f + UtilKTriangle.angleCos(
                        abs(pointF.y - centerPointF.y),
                        sqrt(abs(pointF.x - centerPointF.x) * abs(pointF.x - centerPointF.x) + abs(pointF.y - centerPointF.y) * abs(pointF.y - centerPointF.y))
                )

            pointF.x < centerPointF.x && pointF.y > centerPointF.y ->
                270f + UtilKTriangle.angleCos(
                        abs(pointF.x - centerPointF.x),
                        sqrt(abs(pointF.x - centerPointF.x) * abs(pointF.x - centerPointF.x) + abs(pointF.y - centerPointF.y) * abs(pointF.y - centerPointF.y))
                )

            else -> 0f
        }
    }

    /**
     * 计算临边对斜边的角度在象限中
     * @param pointF MPoint
     * @return Float
     */
    @JvmStatic
    fun angleCosInQuadrant(pointF: MPointF): Float {
        return when {
            pointF.x == 0f && pointF.y >= 0f -> 0f

            pointF.y == 0f && pointF.x >= 0f -> 90f

            pointF.x == 0f && pointF.y < 0f -> 180f

            pointF.y == 0f && pointF.x < 0f -> 270f

            pointF.x > 0f && pointF.y > 0f ->
                UtilKTriangle.angleCos(abs(pointF.y), sqrt(abs(pointF.x) * abs(pointF.x) + abs(pointF.y) * abs(pointF.y)))

            pointF.x > 0f && pointF.y < 0f ->
                90f + UtilKTriangle.angleCos(abs(pointF.x), sqrt(abs(pointF.x) * abs(pointF.x) + abs(pointF.y) * abs(pointF.y)))

            pointF.x < 0f && pointF.y < 0f ->
                180f + UtilKTriangle.angleCos(abs(pointF.y), sqrt(abs(pointF.x) * abs(pointF.x) + abs(pointF.y) * abs(pointF.y)))

            pointF.x < 0f && pointF.y > 0f ->
                270f + UtilKTriangle.angleCos(abs(pointF.x), sqrt(abs(pointF.x) * abs(pointF.x) + abs(pointF.y) * abs(pointF.y)))

            else -> 0f
        }
    }
}