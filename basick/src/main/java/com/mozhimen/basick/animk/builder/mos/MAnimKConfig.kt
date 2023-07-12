package com.mozhimen.basick.animk.builder.mos

import android.view.animation.Interpolator

/**
 * @ClassName MAnimationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/20 14:26
 * @Version 1.0
 */
data class MAnimKConfig(
    var fillBefore: Boolean,
    var fillAfter: Boolean,
    var duration: Long,
    var interpolator: Interpolator
)