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
    private var _arrowDirection: EArrowDirection = EArrowDirection.None
    private var _arrowPosPolicy: EArrowPosPolicy = EArrowPosPolicy.TargetCenter

    private val _originalShape = MShape()
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
                color = _borderColor
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
                color = _fillColor
            }
            return paint.also { field = it }
        }
    private val _fillPath = Path()

    private var _gapWidth = 0f
    private var _fillColor = Color.WHITE
    private var _borderColor = Color.WHITE
    private val _arrowTo = PointF(0f, 0f)


    fun resetRect(width: Int, height: Int) {
        _originalShape.rect[0f, 0f, width.toFloat()] = height.toFloat()
    }

    fun setFillColor(fillColor: Int) {
        _fillColor = fillColor
    }

    fun setBorderColor(borderColor: Int) {
        _borderColor = borderColor
    }

    fun setBorderWidth(borderWidth: Float) {
        _originalShape.borderWidth = borderWidth
    }

    fun setGapPadding(gapPadding: Float) {
        _gapWidth = gapPadding
    }

    fun updateShapes() {
        updateBorderShape()
        updateFillShape()
    }

    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _originalShape.cornerTopLeftRadius = topLeft
        _originalShape.cornerTopRightRadius = topRight
        _originalShape.cornerBottomRightRadius = bottomRight
        _originalShape.cornerBottomLeftRadius = bottomLeft
    }

    fun setArrowDirection(arrowDirection: EArrowDirection) {
        _arrowDirection = arrowDirection
    }

    fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy) {
        _arrowPosPolicy = arrowPosPolicy
    }

    fun setArrowHeight(arrowHeight: Float) {
        _originalShape.arrowHeight = arrowHeight
    }

    fun setArrowWidth(arrowWidth: Float) {
        _originalShape.arrowWidth = arrowWidth
    }

    /**
     * 设置箭头指向的View对象中心相对坐标
     *
     * @param x 目标中心x
     * @param y 目标中心y
     */
    fun setArrowTo(x: Float, y: Float) {
        _arrowTo.x = x
        _arrowTo.y = y
    }

    fun setArrowPosDelta(arrowPosDelta: Float) {
        _originalShape.arrowPosDelta = arrowPosDelta
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
        _borderShape.set(_originalShape)
        _borderShape.rect[
                _originalShape.rect.left + _originalShape.borderWidth / 2f + (if (_arrowDirection.isLeft) _originalShape.arrowHeight else 0f),
                _originalShape.rect.top + _originalShape.borderWidth / 2 + (if (_arrowDirection.isUp) _originalShape.arrowHeight else 0f),
                _originalShape.rect.right - _originalShape.borderWidth / 2 - (if (_arrowDirection.isRight) _originalShape.arrowHeight else 0f)
        ] = _originalShape.rect.bottom - _originalShape.borderWidth / 2 - if (_arrowDirection.isDown) _originalShape.arrowHeight else 0f

        // 外层的箭头顶点位置通过箭头位置策略、箭头偏移设定、目标位置决定
        _shapeDrawer.updateBorderArrowPeak(_arrowDirection, _arrowPosPolicy, _arrowTo, _borderShape)
        _shapeDrawer.updatePath(_arrowDirection, _borderShape, _borderPath)
    }

    private fun updateFillShape() {
        _fillShape.set(_borderShape)

        _fillShape.borderWidth = 0f
        _fillShape.rect[
                _originalShape.rect.left + _originalShape.borderWidth + _gapWidth + (if (_arrowDirection.isLeft) _originalShape.arrowHeight else 0f),
                _originalShape.rect.top + _originalShape.borderWidth + _gapWidth + (if (_arrowDirection.isUp) _originalShape.arrowHeight else 0f),
                _originalShape.rect.right - _originalShape.borderWidth - _gapWidth - (if (_arrowDirection.isRight) _originalShape.arrowHeight else 0f)
        ] = _originalShape.rect.bottom - _originalShape.borderWidth - _gapWidth - if (_arrowDirection.isDown) _originalShape.arrowHeight else 0f

        _fillShape.cornerTopLeftRadius = 0f.coerceAtLeast(_originalShape.cornerTopLeftRadius - _originalShape.borderWidth / 2f - _gapWidth)
        _fillShape.cornerTopRightRadius = 0f.coerceAtLeast(_originalShape.cornerTopRightRadius - _originalShape.borderWidth / 2f - _gapWidth)
        _fillShape.cornerBottomLeftRadius = 0f.coerceAtLeast(_originalShape.cornerBottomLeftRadius - _originalShape.borderWidth / 2f - _gapWidth)
        _fillShape.cornerBottomRightRadius = 0f.coerceAtLeast(_originalShape.cornerBottomRightRadius - _originalShape.borderWidth / 2f - _gapWidth)
        val w =
            _originalShape.arrowWidth - 2 * (_originalShape.borderWidth / 2 + _gapWidth) / sin(atan((_originalShape.arrowHeight / (_originalShape.arrowWidth / 2)).toDouble()))
        val h =
            w * _originalShape.arrowHeight / _originalShape.arrowWidth
        _fillShape.arrowHeight = (h + _originalShape.borderWidth / 2 + _gapWidth).toFloat()
        _fillShape.arrowWidth = _fillShape.arrowHeight * _originalShape.arrowWidth / _originalShape.arrowHeight

        // 内层的箭头顶点位置通过外层边线上的顶点位置来计算
        _shapeDrawer.updateFillArrowPeak(_arrowDirection, _borderShape, _fillShape)
        _shapeDrawer.updatePath(_arrowDirection, _fillShape, _fillPath)
    }
}