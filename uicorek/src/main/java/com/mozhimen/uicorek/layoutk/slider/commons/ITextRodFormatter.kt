package com.mozhimen.uicorek.layoutk.slider.commons

/**
 * @ClassName RegionTextFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:57
 * @Version 1.0
 */
interface ITextRodFormatter {
    fun format(region: Int, value: Float): String
}