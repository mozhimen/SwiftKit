package com.mozhimen.uicorek.drawablek.arrow.commons

import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy

/**
 * @ClassName IArrow
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/16 23:00
 * @Version 1.0
 */
interface IArrow {
    fun setFillColor(fillColor: Int)
    fun setGapWidth(gapWidth: Float)
    fun setCornerRadius(radius: Float)
    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float)
    fun setBorderWidth(borderWidth: Float)
    fun setBorderColor(borderColor: Int)
    fun setArrowWidth(arrowWidth: Float)
    fun setArrowHeight(arrowHeight: Float)
    fun setArrowDirection(arrowDirection: EArrowDirection)
    fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy)
    fun setArrowToPoint(targetCenterX: Float, targetCenterY: Float)
    fun setArrowPosDelta(arrowPosDelta: Float)
}