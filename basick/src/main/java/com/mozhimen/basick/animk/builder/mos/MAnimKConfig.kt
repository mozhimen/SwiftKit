package com.mozhimen.basick.animk.builder.mos

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.mozhimen.basick.utilk.wrapper.UtilKRes

/**
 * @ClassName MAnimationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
data class MAnimKConfig(
    var fillBefore: Boolean = DEFAULT_FILLBEFORE,
    var fillAfter: Boolean = DEFAULT_FILLAFTER,
    var duration: Long = DEFAULT_DURATION,
    var interpolator: Interpolator = DEFAULT_INTERPOLATOR
){
    companion object {
        val DEFAULT_FILLBEFORE = false
        val DEFAULT_FILLAFTER = true
        val DEFAULT_DURATION = 300L
        val DEFAULT_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()
    }
}