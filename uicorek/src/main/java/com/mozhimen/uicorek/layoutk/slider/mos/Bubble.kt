package com.mozhimen.uicorek.layoutk.slider.mos

import android.view.MotionEvent
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.uicorek.layoutk.slider.Slidr

/**
 * @ClassName Bubble
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:58
 * @Version 1.0
 */
class Bubble {
    private var _height = 0f
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

    fun getSliderHeight(): Float {
        return _height - Slidr.BUBBLE_ARROW_HEIGHT.dp2px()
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

    fun onClicked(e: MotionEvent): Boolean {
        return e.x >= _x && e.x <= _x + _width && e.y >= _y && e.y < _y + _height
    }
}