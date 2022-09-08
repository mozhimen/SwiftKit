package com.mozhimen.uicorek.layoutk.slider.mos

import android.graphics.Color

/**
 * @ClassName Step
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 18:01
 * @Version 1.0
 */
class Step(name: String, value: Float, colorBefore: Int, colorAfter: Int) : Comparable<Step> {
    private var _name: String = name
    private var _value: Float = value
    private var _colorBefore: Int = colorBefore
    private var _colorAfter: Int = colorAfter
    private val _startPos = 0f

    override operator fun compareTo(other: Step): Int {
        return _value.compareTo(other._value)
    }
}