package com.mozhimen.uicorek.drawablek.arrow.helpers

import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import com.mozhimen.basick.utilk.exts.normalize
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy
import com.mozhimen.uicorek.drawablek.arrow.mos.MShape

/**
 * @ClassName ShapeDrawer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/14 22:33
 * @Version 1.0
 */
internal class ShapeDrawer {
    private val _ovalRect = RectF()

    fun updatePath(arrowDirection: EArrowDirection, shape: MShape, path: Path) {
        path.reset()
        when (arrowDirection) {
            EArrowDirection.Up -> createPathArrowUp(shape, path)
            EArrowDirection.Down -> createPathArrowDown(shape, path)
            EArrowDirection.Left -> createPathArrowLeft(shape, path)
            EArrowDirection.Right -> createPathArrowRight(shape, path)
            else -> createPathArrowNone(shape, path)
        }
    }

    fun updateFillArrowPeak(arrowDirection: EArrowDirection, borderShape: MShape, outFillShape: MShape) {
        when (arrowDirection) {
            EArrowDirection.Left -> {
                outFillShape.arrowPeakX = outFillShape.rect.left - outFillShape.arrowHeight
                outFillShape.arrowPeakY = borderShape.arrowPeakY
            }
            EArrowDirection.Right -> {
                outFillShape.arrowPeakX = outFillShape.rect.right + outFillShape.arrowHeight
                outFillShape.arrowPeakY = borderShape.arrowPeakY
            }
            EArrowDirection.Up -> {
                outFillShape.arrowPeakX = borderShape.arrowPeakX
                outFillShape.arrowPeakY = outFillShape.rect.top - outFillShape.arrowHeight
            }
            EArrowDirection.Down -> {
                outFillShape.arrowPeakX = borderShape.arrowPeakX
                outFillShape.arrowPeakY = outFillShape.rect.bottom + outFillShape.arrowHeight
            }
            else -> {}
        }
    }

    fun updateBorderArrowPeak(direction: EArrowDirection, policy: EArrowPosPolicy, arrowTo: PointF, outShape: MShape) {
        when (direction) {
            EArrowDirection.Left -> {
                outShape.arrowPeakX = outShape.rect.left - outShape.arrowHeight
                // 确保弧角的显示
                outShape.arrowPeakY =  getLeftRightArrowPeakY(policy, arrowTo, outShape).normalize(
                    outShape.rect.top + outShape.cornerTopLeftRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    outShape.rect.bottom - outShape.cornerBottomLeftRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
            }
            EArrowDirection.Up -> {
                outShape.arrowPeakX = getUpDownArrowPeakX(policy, arrowTo, outShape).normalize(
                    outShape.rect.left + outShape.cornerTopLeftRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    outShape.rect.right - outShape.cornerTopRightRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
                outShape.arrowPeakY = outShape.rect.top - outShape.arrowHeight
            }
            EArrowDirection.Right -> {
                outShape.arrowPeakX = outShape.rect.right + outShape.arrowHeight
                outShape.arrowPeakY = getLeftRightArrowPeakY(policy, arrowTo, outShape).normalize(
                    outShape.rect.top + outShape.cornerTopRightRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    outShape.rect.bottom - outShape.cornerBottomRightRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
            }
            EArrowDirection.Down -> {
                outShape.arrowPeakX = getUpDownArrowPeakX(policy, arrowTo, outShape).normalize(
                    outShape.rect.left + outShape.cornerBottomLeftRadius + outShape.arrowWidth / 2 + outShape.borderWidth / 2,
                    outShape.rect.right - outShape.cornerBottomRightRadius - outShape.arrowWidth / 2 - outShape.borderWidth / 2
                )
                outShape.arrowPeakY = outShape.rect.bottom + outShape.arrowHeight
            }
            else -> {}
        }
    }

    private fun getLeftRightArrowPeakY(policy: EArrowPosPolicy, arrowTo: PointF, shape: MShape): Float {
        var y = 0f
        when (policy) {
            EArrowPosPolicy.TargetCenter -> y = shape.rect.centerY() + arrowTo.y
            EArrowPosPolicy.SelfCenter -> y = shape.rect.centerY()
            EArrowPosPolicy.SelfBegin -> {
                y = shape.rect.top
                y += shape.arrowPosDelta
            }
            EArrowPosPolicy.SelfEnd -> {
                y = shape.rect.bottom
                y -= shape.arrowPosDelta
            }
        }
        return y
    }

    private fun getUpDownArrowPeakX(policy: EArrowPosPolicy, arrowTo: PointF, shape: MShape): Float {
        var x = 0f
        when (policy) {
            EArrowPosPolicy.TargetCenter -> x = shape.rect.centerX() + arrowTo.x
            EArrowPosPolicy.SelfCenter -> x = shape.rect.centerX()
            EArrowPosPolicy.SelfBegin -> {
                x = shape.rect.left
                x += shape.arrowPosDelta
            }
            EArrowPosPolicy.SelfEnd -> {
                x = shape.rect.right
                x -= shape.arrowPosDelta
            }
        }
        return x
    }

    private fun createPathArrowNone(shape: MShape, path: Path) {
        val rect = shape.rect
        path.moveTo(rect.left, rect.top + shape.cornerTopLeftRadius)
        createPathArc(
            path, rect.left, rect.top,
            rect.left + 2 * shape.cornerTopLeftRadius, rect.top + 2 * shape.cornerTopLeftRadius, 180f, 90f
        )
        path.lineTo(rect.right - shape.cornerTopRightRadius, rect.top)
        createPathCornerTopRight(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.cornerBottomRightRadius)
        createPathCornerBottomRight(shape, path)
        path.lineTo(rect.left + shape.cornerBottomLeftRadius, rect.bottom)
        createPathCornerBottomLeft(shape, path)
        path.lineTo(rect.left, rect.top + shape.cornerTopLeftRadius)
    }

    private fun createPathArrowLeft(shape: MShape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY) // 从箭头顶点开始沿顺时针方向绘制
        path.lineTo(rect.left, shape.arrowPeakY - shape.arrowWidth / 2)
        path.lineTo(rect.left, rect.top + shape.cornerTopLeftRadius) // 左上竖线
        createPathCornerTopLeft(shape, path) // 左上弧角
        path.lineTo(rect.right - shape.cornerTopRightRadius, rect.top) // 上横线
        createPathCornerTopRight(shape, path) // 右上弧角
        path.lineTo(rect.right, rect.bottom - shape.cornerBottomRightRadius) // 右侧竖线
        createPathCornerBottomRight(shape, path) // 右下弧角
        path.lineTo(rect.left + shape.cornerBottomLeftRadius, rect.bottom) // 底部横向
        createPathCornerBottomLeft(shape, path) // 左下弧角
        path.lineTo(rect.left, shape.arrowPeakY + shape.arrowWidth / 2) // 左下竖线
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY) // 回到顶点
    }

    private fun createPathArrowUp(shape: MShape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY)
        path.lineTo(shape.arrowPeakX + shape.arrowWidth / 2, rect.top)
        path.lineTo(rect.right - shape.cornerTopRightRadius, rect.top)
        createPathCornerTopRight(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.cornerBottomRightRadius)
        createPathCornerBottomRight(shape, path)
        path.lineTo(rect.left + shape.cornerBottomLeftRadius, rect.bottom)
        createPathCornerBottomLeft(shape, path)
        path.lineTo(rect.left, rect.top + shape.cornerTopLeftRadius)
        createPathCornerTopLeft(shape, path)
        path.lineTo(shape.arrowPeakX - shape.arrowWidth / 2, rect.top)
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY)
    }

    private fun createPathArrowRight(shape: MShape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY)
        path.lineTo(rect.right, shape.arrowPeakY + shape.arrowWidth / 2)
        path.lineTo(rect.right, rect.bottom - shape.cornerBottomRightRadius)
        createPathCornerBottomRight(shape, path)
        path.lineTo(rect.left + shape.cornerBottomLeftRadius, rect.bottom)
        createPathCornerBottomLeft(shape, path)
        path.lineTo(rect.left, rect.top + shape.cornerTopLeftRadius)
        createPathCornerTopLeft(shape, path)
        path.lineTo(rect.right - shape.cornerTopRightRadius, rect.top)
        createPathCornerTopRight(shape, path)
        path.lineTo(rect.right, shape.arrowPeakY - shape.arrowWidth / 2)
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY)
    }

    private fun createPathArrowDown(shape: MShape, path: Path) {
        val rect = shape.rect
        path.moveTo(shape.arrowPeakX, shape.arrowPeakY)
        path.lineTo(shape.arrowPeakX - shape.arrowWidth / 2, rect.bottom)
        path.lineTo(rect.left + shape.cornerBottomLeftRadius, rect.bottom)
        createPathCornerBottomLeft(shape, path)
        path.lineTo(rect.left, rect.top + shape.cornerTopLeftRadius)
        createPathCornerTopLeft(shape, path)
        path.lineTo(rect.right - shape.cornerTopRightRadius, rect.top)
        createPathCornerTopRight(shape, path)
        path.lineTo(rect.right, rect.bottom - shape.cornerBottomRightRadius)
        createPathCornerBottomRight(shape, path)
        path.lineTo(shape.arrowPeakX + shape.arrowWidth / 2, rect.bottom)
        path.lineTo(shape.arrowPeakX, shape.arrowPeakY)
    }

    private fun createPathCornerTopLeft(shape: MShape, path: Path) {
        createPathArc(
            path,
            shape.rect.left,
            shape.rect.top,
            shape.rect.left + 2 * shape.cornerTopLeftRadius,
            shape.rect.top + 2 * shape.cornerTopLeftRadius, 180f, 90f
        )
    }

    private fun createPathCornerTopRight(shape: MShape, path: Path) {
        createPathArc(
            path,
            shape.rect.right - 2 * shape.cornerTopRightRadius,
            shape.rect.top,
            shape.rect.right,
            shape.rect.top + 2 * shape.cornerTopRightRadius, 270f, 90f
        )
    }

    private fun createPathCornerBottomRight(shape: MShape, path: Path) {
        createPathArc(
            path,
            shape.rect.right - 2 * shape.cornerBottomRightRadius,
            shape.rect.bottom - 2 * shape.cornerBottomRightRadius,
            shape.rect.right,
            shape.rect.bottom, 0f, 90f
        )
    }

    private fun createPathCornerBottomLeft(shape: MShape, path: Path) {
        createPathArc(
            path,
            shape.rect.left,
            shape.rect.bottom - 2 * shape.cornerBottomLeftRadius,
            shape.rect.left + 2 * shape.cornerBottomLeftRadius,
            shape.rect.bottom, 90f, 90f
        )
    }

    private fun createPathArc(path: Path, left: Float, top: Float, right: Float, bottom: Float, startAngle: Float, sweepAngle: Float) {
        _ovalRect[left, top, right] = bottom
        path.arcTo(_ovalRect, startAngle, sweepAngle)
    }
}