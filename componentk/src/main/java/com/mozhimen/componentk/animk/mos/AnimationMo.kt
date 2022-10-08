package com.mozhimen.componentk.animk.mos

import android.view.View
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * @ClassName AnimationMo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/8 23:30
 * @Version 1.0
 */
data class AnimationMo(
    val view: View,
    val fillAfter: Boolean = true,
    val repeatCount: Int = Animation.INFINITE,
    val repeatMode: Int = Animation.RESTART,
    val interpolator: Interpolator = LinearInterpolator(),
    val duration: Long = 2000,
    val listener: Animation.AnimationListener? = null
)
