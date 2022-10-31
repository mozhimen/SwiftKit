package com.mozhimen.componentk.animk.mos

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * @ClassName AnimatorMo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/8 23:53
 * @Version 1.0
 */
data class MAnimator(
    val view: View,
    val repeatCount: Int = ValueAnimator.INFINITE,
    val repeatMode: Int = ValueAnimator.RESTART,
    val interpolator: Interpolator = LinearInterpolator(),
    val duration: Long = 2000,
    val listener: Animator.AnimatorListener? = null
)
