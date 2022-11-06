package com.mozhimen.uicorek.layoutk.slider.commons

/**
 * @ClassName Listener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:56
 * @Version 1.0
 */
interface ISliderScrollListener {
    fun onScrollStart()
    fun onScrolling(currentValue: Float)
    fun onScrollEnd(currentValue: Float)
}