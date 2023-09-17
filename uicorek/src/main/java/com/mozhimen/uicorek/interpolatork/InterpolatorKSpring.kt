package com.mozhimen.uicorek.interpolatork

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Interpolator
import kotlin.math.pow
import kotlin.math.sin

/**
 * @ClassName SpringInterpolator
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/12 18:21
 * @Version 1.0
 */
class InterpolatorKSpring : Interpolator {

    constructor()

    constructor(context: Context, attrs: AttributeSet)

    private val _factor: Float = 0.4f

    override fun getInterpolation(input: Float): Float {
        return (2.0.pow(-10.0 * input) * sin((input - _factor / 4) * (2 * Math.PI) / _factor) + 1).toFloat()
    }
}
