package com.mozhimen.uicorek.layoutk.slider.mos

import android.graphics.Color

/**
 * @ClassName Step
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 18:01
 * @Version 1.0
 */
class Step(
    var name: String,
    var value: Float,
    var colorBefore: Int,
    var colorAfter: Int
) : Comparable<Step> {
    var startPos = 0f

    override operator fun compareTo(other: Step): Int {
        return value.compareTo(other.value)
    }
}

/*data class Step(
    var name: String,
    var value: Float,
    var colorBefore: Int,
    var colorAfter: Int
) : Comparable<Step> {
    override fun compareTo(other: Step): Int {
        return value.compareTo(other.value)
    }
}*/
