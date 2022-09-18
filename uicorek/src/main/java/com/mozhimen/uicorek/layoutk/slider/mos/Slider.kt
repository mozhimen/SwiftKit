package com.mozhimen.uicorek.layoutk.slider.mos

import android.graphics.Color
import androidx.annotation.ColorRes
import com.mozhimen.uicorek.layoutk.slider.helpers.LayoutKSliderParser

/**
 * @ClassName Slider
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/17 15:48
 * @Version 1.0
 */
class Slider {
    var width: Float = 0f
        set(value) {
            field = if (value <= 0) LayoutKSliderParser.SLIDER_WIDTH else value
            refreshX()
        }
    var height: Float = 0f
        set(value) {
            field = if (value <= 0) LayoutKSliderParser.SLIDER_HEIGHT else value
            refreshY()
        }
    var leftX: Float = 0f
        set(value) {
            field = value
            refreshX()
        }
    var topY: Float = 0f
        set(value) {
            field = value
            refreshY()
        }
    var rodLeftColor = Color.BLACK
    var rodRightColor = Color.LTGRAY
    var widthHalf: Float = 0f
    var centerX: Float = 0f
    var rightX: Float = 0f
    var heightHalf: Float = 0f
    var centerY: Float = 0f
    var bottomY: Float = 0f

    constructor(width: Float, height: Float, leftX: Float, topY: Float) {
        this.width = width
        this.height = height
        this.leftX = leftX
        this.topY = topY
    }

    fun refreshX() {
        widthHalf = width / 2f
        centerX = leftX + widthHalf
        rightX = leftX + width
    }

    fun refreshY() {
        heightHalf = height / 2f
        centerY = topY + heightHalf
        bottomY = topY + height
    }
}