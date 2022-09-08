package com.mozhimen.uicorek.layoutk.slider.commons

import com.mozhimen.uicorek.layoutk.slider.Slidr

/**
 * @ClassName Listener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:56
 * @Version 1.0
 */
interface Listener {
    fun valueChanged(slidr: Slidr, currentValue: Float)
}