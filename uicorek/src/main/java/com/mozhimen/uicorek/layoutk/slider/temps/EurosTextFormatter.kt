package com.mozhimen.uicorek.layoutk.slider.temps

import com.mozhimen.uicorek.layoutk.slider.commons.TextFormatter

/**
 * @ClassName EurosTextFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:59
 * @Version 1.0
 */
class EurosTextFormatter : TextFormatter {
    override fun format(value: Float): String {
        return String.format("%d €", value.toInt())
    }
}