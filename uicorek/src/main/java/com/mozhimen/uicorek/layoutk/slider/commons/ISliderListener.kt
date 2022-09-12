package com.mozhimen.uicorek.layoutk.slider.commons

import com.mozhimen.uicorek.layoutk.slider.LayoutKSlider

/**
 * @ClassName Listener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:56
 * @Version 1.0
 */
interface ISliderListener {
    fun onScroll(layoutKSlider: LayoutKSlider, currentValue: Float)
}