package com.mozhimen.uicorek.layoutk.slider.commons

import com.mozhimen.uicorek.layoutk.slider.mos.MRod

/**
 * @ClassName Listener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:56
 * @Version 1.0
 */
interface ISliderScrollListener {
    fun onScrollStart()
    fun onScrolling(currentPercent: Float, currentValue: Float, rod: MRod)
    fun onScrollEnd(currentPercent: Float, currentValue: Float, rod: MRod)
}