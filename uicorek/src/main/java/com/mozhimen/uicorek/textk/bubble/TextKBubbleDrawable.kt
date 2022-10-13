package com.mozhimen.uicorek.textk.bubble

import android.graphics.*
import android.graphics.drawable.Drawable
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowDirection
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowPosPolicy
import kotlin.math.atan
import kotlin.math.sin

/**
 * @ClassName TextKBubbleDrawable
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/6 16:32
 * @Version 1.0
 */
class TextKBubbleDrawable : Drawable() {
    //region # variate
    private var _arrowDirection: ArrowDirection = ArrowDirection.None
    private var _arrowPosPolicy: ArrowPosPolicy = ArrowPosPolicy.TargetCenter

    private val _originalShape = Shape()
    private val _borderShape = Shape()
    private val _fillShape = Shape()

    private val _borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val _borderPath = Path()
    private val _fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val _fillPath = Path()

    private var _gapPadding = 0f
    private var _bgColor = Color.WHITE
    private var _borderColor = Color.WHITE
    private val _arrowTo = PointF(0f, 0f)

    private val _ovalRect = RectF()
    //endregion

    fun resetRect(width: Int, height: Int) {
        _originalShape.rect[0f, 0f, width.toFloat()] = height.toFloat()
    }

    fun setBgColor(bgColor: Int) {
        _bgColor = bgColor
    }

    fun setBorderColor(borderColor: Int) {
        _borderColor = borderColor
    }

    fun setBorderWidth(borderWidth: Float) {
        _originalShape.borderWidth = borderWidth
    }

    fun setGapPadding(gapPadding: Float) {
        _gapPadding = gapPadding
    }

    fun updateShapes() {
        updateBorderShape()
        updateFillShape()
    }

    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _originalShape.topLeftRadius = topLeft
        _originalShape.topRightRadius = topRight
        _originalShape.bottomRightRadius = bottomRight
        _originalShape.bottomLeftRadius = bottomLeft
    }

    fun setArrowDirection(arrowDirection: ArrowDirection) {
        _arrowDirection = arrowDirection
    }

    fun setArrowPosPolicy(arrowPosPolicy: ArrowPosPolicy) {
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

    fun setArrowPosDelta(arrowDelta: Float) {
        _originalShape.arrowDelta = arrowDelta
    }

    override fun draw(canvas: Canvas) {
        _fillPaint.style = Paint.Style.FILL
        _fillPaint.color = _bgColor
        canvas.drawPath(_fillPath, _fillPaint)
        if (_borderShape.borderWidth > 0) {
            _borderPaint.style = Paint.Style.STROKE
            _borderPaint.strokeCap = Paint.Cap.ROUND
            _borderPaint.strokeJoin = Paint.Join.ROUND
            _borderPaint.strokeWidth = _borderShape.borderWidth
            _borderPaint.color = _borderColor
            canvas.drawPath(_borderPath, _borderPaint)
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    //region # private func
    private class Shape {
        var rect = RectF()
        var borderWidth = 0f
        var arrowHeight = 0f
        var arrowWidth = 0f
        var arrowDelta = 0f
        var arrowPeakX = 0f
        var arrowPeakY = 0f
        var topLeftRadius = 0f
        var topRightRadius = 0f
        var bottomLeftRadius = 0f
        var bottomRightRadius = 0f

        fun set(shape: Shape) {
            this.rect.set(shape.rect)
            this.borderWidth = shape.borderWidth
            this.arrowHeight = shape.arrowHeight
            this.arrowWidth = shape.arrowWidth
            this.arrowDelta = shape.arrowDelta
            this.arrowPeakX = shape.arrowPeakX
            this.arrowPeakY = shape.arrowPeakY
            this.topLeftRadius = shape.topLeftRadius
            this.topRightRadius = shape.topRightRadius
            this.bottomLeftRadius = shape.bottomLeftRadius
            this.bottomRightRadius = shape.bottomRightRadius
        }
    }

    private fun getLeftRightArrowPeakY(policy: ArrowPosPolicy, arrowTo: PointF, shape: Shape): Float {
        var y = 0f
        when (policy) {
            ArrowPosPolicy.TargetCenter -> y = shape.rect.centerY() + arrowTo.y
            ArrowPosPolicy.SelfCenter -> y = shape.rect.centerY()
            ArrowPosPolicy.SelfBegin -> {
                y = shape.rect.top
                y += shape.arrowDelta
            }
            ArrowPosPolicy.SelfEnd -> {
                y = shape.rect.bottom
                y -= shape.arrowDelta
            }
        }
        return y
    }

    private fun getUpDownArrowPeakX(policy: ArrowPosPolicy, arrowTo: PointF, shape: Shape): Float {
        var x = 0f
        when (policy) {
            ArrowPosPolicy.TargetCenter -> x = shape.rect.centerX() + arrowTo.x
            ArrowPosPolicy.SelfCenter -> x = shape.rect.centerX()
            ArrowPosPolicy.SelfBegin -> {
                x = shape.rect.left
                x += shape.arrowDelta
            }
            ArrowPosPolicy.SelfEnd -> {
                x = shape.rect.right
                x -= shape.arrowDelta
            }
        }
        return x
    }

    private fun updateBorderShape() {
        _borderShape.set(_originalShape)
        _borderShape.rect[
                _originalShape.rect.left + _originalShape.borderWidth / 2f + (if (_arrowDirection.isLeft) _originalShape.arrowHeight else 0f),
                _originalShape.rect.top + _originalShape.borderWidth / 2 + (if (_arrowDirection.isUp) _originalShape.arrowHeight else 0f),
                _originalShape.rect.right - _originalShape.borderWidth / 2 - (if (_arrowDirection.isRight) _originalShape.arrowHeight else 0f)
        ] = _originalShape.rect.bottom - _originalShape.borderWidth / 2 - if (_arrowDirection.isDown) _originalShape.arrowHeight else 0f

        // 外层的箭头顶点位置通过箭头位置策略、箭头偏移设定、目标位置决定
        updateBorderArrowPeak(_arrowDirection, _arrowPosPolicy, _arrowTo, _borderShape)
        updatePath(_borderShape, _borderPath)
    }

    private fun updateFillShape() {
        _fillShape.set(_borderShape)

        _fillShape.borderWidth = 0f
        _fillShape.rect[
                _originalShape.rect.left + _originalShape.borderWidth + _gapPadding + (if (_arrowDirection.isLeft) _originalShape.arrowHeight else 0f),
                _originalShape.rect.top + _originalShape.borderWidth + _gapPadding + (if (_arrowDirection.isUp) _originalShape.arrowHeight else 0f),
                _originalShape.rect.right - _originalShape.borderWidth - _gapPadding - (if (_arrowDirection.isRight) _originalShape.arrowHeight else 0f)
        ] = _originalShape.rect.bottom - _originalShape.borderWidth - _gapPadding - if (_arrowDirection.isDown) _originalShape.arrowHeight else 0f

        _fillShape.topLeftRadius = 0f.coerceAtLeast(_originalShape.topLeftRadius - _originalShape.borderWidth / 2f - _gapPadding)
        _fillShape.topRightRadius = 0f.coerceAtLeast(_originalShape.topRightRadius - _originalShape.borderWidth / 2f - _gapPadding)
        _fillShape.bottomLeftRadius = 0f.coerceAtLeast(_originalShape.bottomLeftRadius - _originalShape.borderWidth / 2f - _gapPadding)
        _fillShape.bottomRightRadius = 0f.coerceAtLeast(_originalShape.bottomRightRadius - _originalShape.borderWidth / 2f - _gapPadding)
        val w =
            _originalShape.arrowWidth - 2 * (_originalShape.borderWidth / 2 + _gapPadding) / sin(atan((_originalShape.arrowHeight / (_originalShape.arrowWidth / 2)).toDouble()))
        val h =
            w * _originalShape.arrowHeight / _originalShape.arrowWidth
        _fillShape.arrowHeight = (h + _originalShape.borderWidth / 2 + _gapPadding).toFloat()
        _fillShape.arrowWidth = _fillShape.arrowHeight * _originalShape.arrowWidth / _originalShape.arrowHeight

        // 内层的箭头顶点位置通过外层边线上的顶点位置来计算
        updateFillArrowPeak(_arrowDirection, _borderShape, _fillShape)
        updatePath(_fillShape, _fillPath)
    }

    private fun updateFillArrowPeak(direction: ArrowDirection, borderShape: Shape, outFillShape: Shape) {
        when (direction) {
            ArrowDirection.Left -> {
                outFillShape.arrowPeakX = outFillShape.rect.left - outFillShape.arrowHeight
                outFillShape.arrowPeakY = borderShape.arrowPeakY
            }
            ArrowDirection.Right -> {
                outFillShape.arrowPeakX = outFillShape.rect.right + outFillShape.arrowHeight
                outFillShape.arrowPeakY = borderShape.arrowPeakY
            }
            ArrowDirection.Up -> {
                outFillShape.arrowPeakX = borderShape.arrowPeakX
                outFillShape.arrowPeakY = outFillShape.rect.top - outFillShape.arrowHeight
            }
            ArrowDirection.Down -> {
                outFillShape.arrowPeakX = borderShape.arrowPeakX
                outFillShape.arrowPeakY = outFillShape.rect.bottom + outFillShape.arrowHeight
            }
            else -> {}
        }
    }

    private fun updateBorderArrowPeak(direction: ArrowDirection, policy: ArrowPosPolicy, arrowTo: PointF, outShape: Shape) {
        when (direction) {
            ArrowDirection.Left -> {
                outShape.arrowPeakX = outShape.rect.left - outShape.arrowHeight
                outShape.arrowPeakY = bound(
                    outShape.rect.top + outShape.topLeftRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    getLeftRightArrowPeakY(policy, arrowTo, outShape),  // 确保弧角的显示
                    outShape.rect.bottom - outShape.bottomLeftRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
            }
            ArrowDirection.Up -> {
                outShape.arrowPeakX = bound(
                    outShape.rect.left + outShape.topLeftRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    getUpDownArrowPeakX(policy, arrowTo, outShape),
                    outShape.rect.right - outShape.topRightRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
                outShape.arrowPeakY = outShape.rect.top - outShape.arrowHeight
            }
            ArrowDirection.Right -> {
                outShape.arrowPeakX = outShape.rect.right + outShape.arrowHeight
                outShape.arrowPeakY = bound(
                    outShape.rect.top + outShape.topRightRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    getLeftRightArrowPeakY(policy, arrowTo, outShape),
                    outShape.rect.bottom - outShape.bottomRightRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
            }
            ArrowDirection.Down -> {
                outShape.arrowPeakX = bound(
                    outShape.rect.left + outShape.bottomLeftRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    getUpDownArrowPeakX(policy, arrowTo, outShape),
                    outShape.rect.right - outShape.bottomRightRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
                outShape.arrowPeakY = outShape.rect.bottom + outShape.arrowHeight
            }
            else -> {}
        }
    }

    private fun updatePath(shape: Shape, path: Path) {
        path.reset()
        when (_arrowDirection) {
            ArrowDirection.Up -> buildWithUpArrow(shape, path)
            ArrowDirection.Down -> buildWithDownArrow(shape, path)
            ArrowDirection.Left -> buildWithLeftArrow(shape, path)
            ArrowDirection.Right -> buildWithRightArrow(shape, path)
            else -> buildWithNoneArrow(shape, path)
        }
    }

    private fun buildWithNoneArrow(shape: Shape, path: Path) {
        val rect = shape.rect
        path.moveTo(rect.left, rect.top + shape.topLeftRadius)
        compatPathArcTo(
            path, rect.left, rect.top,
            rect.left + 2 * shape.topLeftRadius, rect.top + 2 * shape.topLeftRadius, 180f, 90f
        )
        path.lineTo(rect.right - shape.topRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.bottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(rect.left + shape.bottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.topLeftRadius)
    }

    private fun buildWithLeftArrow(shape: Shape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY) // 从箭头顶点开始沿顺时针方向绘制
        path.lineTo(rect.left, shape.arrowPeakY - shape.arrowWidth / 2)
        path.lineTo(rect.left, rect.top + shape.topLeftRadius) // 左上竖线
        buildTopLeftCorner(shape, path) // 左上弧角
        path.lineTo(rect.right - shape.topRightRadius, rect.top) // 上横线
        buildTopRightCorner(shape, path) // 右上弧角
        path.lineTo(rect.right, rect.bottom - shape.bottomRightRadius) // 右侧竖线
        buildBottomRightCorner(shape, path) // 右下弧角
        path.lineTo(rect.left + shape.bottomLeftRadius, rect.bottom) // 底部横向
        buildBottomLeftCorner(shape, path) // 左下弧角
        path.lineTo(rect.left, shape.arrowPeakY + shape.arrowWidth / 2) // 左下竖线
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY) // 回到顶点
    }

    private fun buildWithUpArrow(shape: Shape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY)
        path.lineTo(shape.arrowPeakX + shape.arrowWidth / 2, rect.top)
        path.lineTo(rect.right - shape.topRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.bottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(rect.left + shape.bottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.topLeftRadius)
        buildTopLeftCorner(shape, path)
        path.lineTo(shape.arrowPeakX - shape.arrowWidth / 2, rect.top)
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY)
    }

    private fun buildWithRightArrow(shape: Shape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY)
        path.lineTo(rect.right, shape.arrowPeakY + shape.arrowWidth / 2)
        path.lineTo(rect.right, rect.bottom - shape.bottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(rect.left + shape.bottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.topLeftRadius)
        buildTopLeftCorner(shape, path)
        path.lineTo(rect.right - shape.topRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, shape.arrowPeakY - shape.arrowWidth / 2)
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY)
    }

    private fun buildWithDownArrow(shape: Shape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY)
        path.lineTo(shape.arrowPeakX - shape.arrowWidth / 2, rect.bottom)
        path.lineTo(rect.left + shape.bottomLeftRadius, rect.bottom)
        buildBottomLeftCorner(shape, path)
        path.lineTo(rect.left, rect.top + shape.topLeftRadius)
        buildTopLeftCorner(shape, path)
        path.lineTo(rect.right - shape.topRightRadius, rect.top)
        buildTopRightCorner(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.bottomRightRadius)
        buildBottomRightCorner(shape, path)
        path.lineTo(shape.arrowPeakX + shape.arrowWidth / 2, rect.bottom)
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY)
    }

    private fun buildTopLeftCorner(shape: Shape, path: Path) {
        compatPathArcTo(
            path,
            shape.rect.left,
            shape.rect.top,
            shape.rect.left + 2 * shape.topLeftRadius,
            shape.rect.top + 2 * shape.topLeftRadius, 180f, 90f
        )
    }

    private fun buildTopRightCorner(shape: Shape, path: Path) {
        compatPathArcTo(
            path,
            shape.rect.right - 2 * shape.topRightRadius,
            shape.rect.top,
            shape.rect.right,
            shape.rect.top + 2 * shape.topRightRadius, 270f, 90f
        )
    }

    private fun buildBottomRightCorner(shape: Shape, path: Path) {
        compatPathArcTo(
            path,
            shape.rect.right - 2 * shape.bottomRightRadius,
            shape.rect.bottom - 2 * shape.bottomRightRadius,
            shape.rect.right,
            shape.rect.bottom, 0f, 90f
        )
    }

    private fun buildBottomLeftCorner(shape: Shape, path: Path) {
        compatPathArcTo(
            path,
            shape.rect.left,
            shape.rect.bottom - 2 * shape.bottomLeftRadius,
            shape.rect.left + 2 * shape.bottomLeftRadius,
            shape.rect.bottom, 90f, 90f
        )
    }

    private fun compatPathArcTo(path: Path, left: Float, top: Float, right: Float, bottom: Float, startAngle: Float, sweepAngle: Float) {
        _ovalRect[left, top, right] = bottom
        path.arcTo(_ovalRect, startAngle, sweepAngle)
    }

    private fun bound(min: Float, value: Float, max: Float): Float {
        return value.coerceAtLeast(min).coerceAtMost(max)
    }
    //endregion
}