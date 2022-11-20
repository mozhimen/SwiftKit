package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName RotateRecyclerType
 * @Description 不断旋转
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/20 15:50
 * @Version 1.0
 */
class RotationRecyclerType : RotationType() {

    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnimation(animKConfig: AnimKConfig, animation: Animation) {
        super.formatAnimation(animKConfig, animation)
        animation.apply {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.RESTART
        }
    }

    override fun formatAnimator(animKConfig: AnimKConfig, animator: Animator) {
        super.formatAnimator(animKConfig, animator)
        (animator as ObjectAnimator).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
        }
    }
}