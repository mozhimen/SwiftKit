package com.mozhimen.uicorek.drawablek.arrow

import android.graphics.*
import android.graphics.drawable.Drawable
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy
import com.mozhimen.uicorek.drawablek.arrow.mos.MShape

/**
 * @ClassName DrawableKArrow
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/13 22:30
 * @Version 1.0
 */
class DrawableKArrow : Drawable() {
    private val _shape = MShape()
    private val _borderPath = Path()
    private var _borderPaint: Paint? = null
        get() {
            if (field != null) return field
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.apply {
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
                strokeWidth = _shape.borderWidth
                color = _shape.borderColor
            }
            return paint.also { field = it }
        }

    private val _fillPath = Path()
    private var _fillPaint: Paint? = null
        get() {
            if (field != null) return field
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.apply {
                style = Paint.Style.FILL
                color = _shape.fillColor
            }
            return paint.also { field = it }
        }

    //region # public func
    fun resetRect(width: Int, height: Int) {
        _shape.rect[0f, 0f, width.toFloat()] = height.toFloat()
    }

    fun setFillColor(color: Int) {
        _shape.fillColor = color
    }

    fun setBorderColor(color: Int) {
        _shape.borderColor = color
    }

    fun setBorderWidth(borderWidth: Float) {
        _shape.borderWidth = borderWidth
    }

    fun setGapWidth(gapWidth: Float) {
        _shape.gapWidth = gapWidth
    }

    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _shape.cornerTopLeftRadius = topLeft
        _shape.cornerTopRightRadius = topRight
        _shape.cornerBottomRightRadius = bottomRight
        _shape.cornerBottomLeftRadius = bottomLeft
    }

    fun setArrowDirection(arrowDirection: EArrowDirection) {
        _shape.arrowDirection = arrowDirection
    }

    fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy) {
        _shape.arrowPosPolicy = arrowPosPolicy
    }

    fun setArrowHeight(arrowHeight: Float) {
        _shape.arrowHeight = arrowHeight
    }

    fun setArrowWidth(arrowWidth: Float) {
        _shape.arrowWidth = arrowWidth
    }

    /**
     * 设置箭头指向的View对象中心相对坐标
     *
     * @param x 目标中心x
     * @param y 目标中心y
     */
    fun setArrowTo(x: Float, y: Float) {
        _shape.arrowToX = x
        _shape.arrowToX = y
    }

    fun setArrowPosDelta(arrowDelta: Float) {
        _shape.arrowPosDelta = arrowDelta
    }

    fun updateShapes() {
//        updateBorderShape()
//        updateFillShape()
    }
    //endregion

    override fun draw(canvas: Canvas) {
        canvas.drawPath(_fillPath, _fillPaint!!)
        if (_shape.borderWidth > 0) {
            canvas.drawPath(_borderPath, _borderPaint!!)
        }
    }

    override fun setAlpha(p0: Int) {
    }

    override fun setColorFilter(p0: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }
}