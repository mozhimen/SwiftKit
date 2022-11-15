package com.mozhimen.uicorek.drawablek.arrow

import android.graphics.*
import android.graphics.drawable.Drawable
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy
import com.mozhimen.uicorek.drawablek.arrow.helpers.ShapeDrawer
import com.mozhimen.uicorek.drawablek.arrow.mos.MShape
import kotlin.math.atan
import kotlin.math.sin

/**
 * @ClassName DrawableKArrow
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/13 22:30
 * @Version 1.0
 */
class DrawableKArrow : Drawable() {
    private val _shapeDrawer: ShapeDrawer by lazy { ShapeDrawer() }
    private val _shape = MShape()
    private val _borderShape = MShape()
    private val _fillShape = MShape()

    private var _borderPaint: Paint? = null
        get() {
            if (field != null) return field
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.apply {
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
                strokeWidth = _borderShape.borderWidth
                color = _shape.borderColor
            }
            return paint.also { field = it }
        }
    private val _borderPath = Path()
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
    private val _fillPath = Path()

    fun resetRect(width: Int, height: Int) {
        _shape.rect[0f, 0f, width.toFloat()] = height.toFloat()
    }

    fun setFillColor(fillColor: Int) {
        _shape.fillColor = fillColor
    }

    fun setGapWidth(gapWidth: Float) {
        _shape.gapWidth = gapWidth
    }

    fun setCornerRadius(radius: Float) {
        setCornerRadius(radius, radius, radius, radius)
    }

    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _shape.cornerTopLeftRadius = topLeft
        _shape.cornerTopRightRadius = topRight
        _shape.cornerBottomRightRadius = bottomRight
        _shape.cornerBottomLeftRadius = bottomLeft
    }

    fun setBorderWidth(borderWidth: Float) {
        _shape.borderWidth = borderWidth
    }

    fun setBorderColor(borderColor: Int) {
        _shape.borderColor = borderColor
    }

    /**
     * 在EArrowDirection.None无效
     * @param arrowWidth Float
     */
    fun setArrowWidth(arrowWidth: Float) {
        _shape.arrowWidth = arrowWidth
    }

    /**
     * 在EArrowDirection.None无效
     * @param arrowHeight Float
     */
    fun setArrowHeight(arrowHeight: Float) {
        _shape.arrowHeight = arrowHeight
    }

    fun setArrowDirection(arrowDirection: EArrowDirection) {
        _shape.arrowDirection = arrowDirection
    }

    fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy) {
        _shape.arrowPosPolicy = arrowPosPolicy
    }

    /**
     * 设置箭头指向的View对象中心相对坐标,EArrowPosPolicy.TargetCenter有效
     * @param targetCenterX 目标中心x
     * @param targetCenterY 目标中心y
     */
    fun setArrowToPoint(targetCenterX: Float, targetCenterY: Float) {
        _shape.arrowToPoint.apply {
            this.x = targetCenterX
            this.y = targetCenterY
        }
//        _arrowTo.x = x
//        _arrowTo.y = y
    }

    /**
     * EArrowPosPolicy.SelfBegin或EArrowPosPolicy.SelfEnd有效
     * @param arrowPosDelta Float
     */
    fun setArrowPosDelta(arrowPosDelta: Float) {
        _shape.arrowPosDelta = arrowPosDelta
    }

    fun updateShapes() {
        updateBorderShape()
        updateFillShape()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(_fillPath, _fillPaint!!)
        if (_borderShape.borderWidth > 0) {
            canvas.drawPath(_borderPath, _borderPaint!!)
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    private fun updateBorderShape() {
        _borderShape.set(_shape)
        _borderShape.rect[
                _shape.rect.left + _shape.borderWidth / 2f + (if (_shape.arrowDirection.isLeft) _shape.arrowHeight else 0f),
                _shape.rect.top + _shape.borderWidth / 2 + (if (_shape.arrowDirection.isUp) _shape.arrowHeight else 0f),
                _shape.rect.right - _shape.borderWidth / 2 - (if (_shape.arrowDirection.isRight) _shape.arrowHeight else 0f)
        ] = _shape.rect.bottom - _shape.borderWidth / 2 - if (_shape.arrowDirection.isDown) _shape.arrowHeight else 0f

        // 外层的箭头顶点位置通过箭头位置策略、箭头偏移设定、目标位置决定
        _shapeDrawer.updateBorderArrowPeak(_shape.arrowDirection, _shape.arrowPosPolicy, /*_arrowTo*/_shape.arrowToPoint, _borderShape)
        _shapeDrawer.updatePath(_shape.arrowDirection, _borderShape, _borderPath)
    }

    private fun updateFillShape() {
        _fillShape.set(_borderShape)

        _fillShape.borderWidth = 0f
        _fillShape.rect[
                _shape.rect.left + _shape.borderWidth + _shape.gapWidth + (if (_shape.arrowDirection.isLeft) _shape.arrowHeight else 0f),
                _shape.rect.top + _shape.borderWidth + _shape.gapWidth + (if (_shape.arrowDirection.isUp) _shape.arrowHeight else 0f),
                _shape.rect.right - _shape.borderWidth - _shape.gapWidth - (if (_shape.arrowDirection.isRight) _shape.arrowHeight else 0f)
        ] = _shape.rect.bottom - _shape.borderWidth - _shape.gapWidth - if (_shape.arrowDirection.isDown) _shape.arrowHeight else 0f

        _fillShape.cornerTopLeftRadius = 0f.coerceAtLeast(_shape.cornerTopLeftRadius - _shape.borderWidth / 2f - _shape.gapWidth)
        _fillShape.cornerTopRightRadius = 0f.coerceAtLeast(_shape.cornerTopRightRadius - _shape.borderWidth / 2f - _shape.gapWidth)
        _fillShape.cornerBottomLeftRadius = 0f.coerceAtLeast(_shape.cornerBottomLeftRadius - _shape.borderWidth / 2f - _shape.gapWidth)
        _fillShape.cornerBottomRightRadius = 0f.coerceAtLeast(_shape.cornerBottomRightRadius - _shape.borderWidth / 2f - _shape.gapWidth)
        val w = _shape.arrowWidth - 2 * (_shape.borderWidth / 2 + _shape.gapWidth) / sin(atan((_shape.arrowHeight / (_shape.arrowWidth / 2)).toDouble()))
        val h = w * _shape.arrowHeight / _shape.arrowWidth
        _fillShape.arrowHeight = (h + _shape.borderWidth / 2 + _shape.gapWidth).toFloat()
        _fillShape.arrowWidth = _fillShape.arrowHeight * _shape.arrowWidth / _shape.arrowHeight

        // 内层的箭头顶点位置通过外层边线上的顶点位置来计算
        _shapeDrawer.updateFillArrowPeak(_shape.arrowDirection, _borderShape, _fillShape)
        _shapeDrawer.updatePath(_shape.arrowDirection, _fillShape, _fillPath)
    }
}