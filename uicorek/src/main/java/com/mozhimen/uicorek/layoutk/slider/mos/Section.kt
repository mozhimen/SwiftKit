package com.mozhimen.uicorek.layoutk.slider.mos

/**
 * @ClassName Step
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 18:01
 * @Version 1.0
 */
class Section(
    var name: String,
    var value: Float,
    var colorBefore: Int,
    var colorAfter: Int
) : Comparable<Section> {
    var startPos = 0f

    override operator fun compareTo(other: Section): Int {
        return value.compareTo(other.value)
    }
}
