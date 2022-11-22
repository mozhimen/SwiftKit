package com.mozhimen.uicorek.layoutk.slider.mos

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.utilk.exts.normalize
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.layoutk.slider.helpers.AttrsParser

/**
 * @ClassName Rod
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/17 23:50
 * @Version 1.0
 */
class MRod(private val _owner: LifecycleOwner) {
    companion object {
        private val TAG = "Rod>>>>>"
    }

    //region # layout
    var minX: Float = 0f
    var maxX: Float = 0.1f
    val centerX: Float
        get() = intervalX / 2f
    var centerY: Float = 0f
    val currentX: Float
        get() = currentPercent * intervalX + minX
    var currentPercent: Float = 0f
        set(value) {
            field = value.normalize(0f to 1f)
            _sliderListener?.onScrolling(currentPercent, currentVal, this)
        }
    val currentVal: Float
        get() = currentPercent * intervalVal
    val intervalX: Float
        get() = maxX - minX
    val intervalVal: Float
        get() = maxVal - minVal
    //endregion

    //region # attrs
    var isInsideSlider: Boolean = false
    var radius: Float = 0f
    var radiusInside: Float = 0f
        set(value) {
            field = value.normalize(0f to radius)
        }
    var minVal: Float = 0f
    var maxVal: Float = 100f
    var rodColor: Int = AttrsParser.ROD_COLOR
    var rodColorInside: Int = AttrsParser.ROD_COLOR_INSIDE
    //endregion

    private var _sliderListener: ISliderScrollListener? = null
//    private suspend fun launchScrollChange(rod: MRod) = flow {
//        emit(rod)
//    }.flowOn(Dispatchers.IO).collectLatest {//这里我希望不要频繁的更新UI, 所以采用背压的方式来获取频繁数据的最新值
//
//    }

//    flow<String>
//    {
//
//    }.flowOn(Dispatchers.Main)

//    constructor(minX: Float, maxX: Float, centerY: Float, isInsideSlider: Boolean, radius: Float, radiusInside: Float, _sliderListener: ISliderScrollListener?) {
//        this.minX = minX
//        this.maxX = maxX
//        this.currentX = minX
//        this.centerY = centerY
//        this.isInsideSlider = isInsideSlider
//        this.radius = radius
//        this.radiusInside = radiusInside
//        _sliderListener?.let { this._sliderListener = it }
//    }
}