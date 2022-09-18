package com.mozhimen.uicorek.layoutk.slider.mos

import android.view.MotionEvent
import com.mozhimen.basick.utilk.UtilKGesture

/**
 * @ClassName Bubble
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:58
 * @Version 1.0
 */
class Bubble {
    var width = 0f
    var height = 0f
    var leftX = 0f
    var rightX = 0f
    var topY = 0f
    var bottomY = 0f
    var arrowWidth = 0f
    var arrowHeight = 0f
    var bubbleHeight: Float = 0f
        get() = height - arrowHeight

    fun isTapInArea(e: MotionEvent): Boolean =
        UtilKGesture.isTapInArea(e, leftX, rightX, topY, bottomY)

    /*private var _height = 0f
    private var _width = 0f
    private var _x = 0f
    private var _y = 0f

    fun setWidth(width: Float) {
        _width = width
    }

    fun getWidth(): Float =
        _width

    fun setHeight(height: Float) {
        _height = height
    }

    fun getHeight(): Float =
        _height

    fun getBubbleHeight(): Float {
        return _height - BUBBLE_ARROW_HEIGHT.dp2px()
    }

    fun setX(x: Float) {
        _x = x
    }

    fun getX(): Float {
        return _x.coerceAtLeast(0f)
    }

    fun setY(y: Float) {
        _y = y
    }

    fun getY(): Float {
        return _y.coerceAtLeast(0f)
    }

    fun isTapInBubbleArea(e: MotionEvent): Boolean {
        return e.x >= _x && e.x <= _x + _width && e.y >= _y && e.y < _y + _height
    }*/
}