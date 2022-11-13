package com.mozhimen.uicorek.layoutk.slider.mos

import android.util.Log
import com.mozhimen.basick.extsk.normalize
import com.mozhimen.basick.extsk.percent
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener

/**
 * @ClassName Rod
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/17 23:50
 * @Version 1.0
 */
class MRod {
    private val TAG = "Rod>>>>>"
    var minX: Float = 0f
    var maxX: Float = 0f
    val intervalX: Float
        get() = maxX - minX
    var centerY: Float = 0f
    val centerX: Float
        get() = (maxX - minX) / 2f
    var isInsideSlider: Boolean = false
    var radius: Float = 0f
    var radiusInside: Float = 0f
        set(value) {
            field = value.normalize(0f to radius)
        }

    var currentX: Float = 0f
        set(value) {
            field = value.normalize(minX to maxX)
            _sliderListener?.onScrolling(currentPercent, currentVal, this)
        }
    val currentPercent: Float
        get() = currentX.percent(minX to maxX)

    var minVal: Float = 0f
    var maxVal: Float = 100f
    val intervalVal: Float
        get() = maxVal - minVal
    val currentVal: Float
        get() = currentPercent * intervalVal

    private var _sliderListener: ISliderScrollListener? = null

    constructor()

    constructor(minX: Float, maxX: Float, centerY: Float, isInsideSlider: Boolean, radius: Float, radiusInside: Float, _sliderListener: ISliderScrollListener?) {
        this.minX = minX
        this.maxX = maxX
        this.currentX = minX
        this.centerY = centerY
        this.isInsideSlider = isInsideSlider
        this.radius = radius
        this.radiusInside = radiusInside
        _sliderListener?.let { this._sliderListener = it }
    }
}