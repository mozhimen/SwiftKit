package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName RotateRecyclerType
 * @Description 不断旋转
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/20 15:50
 * @Version 1.0
 */
class AnimKRotationRecyclerType : AnimKRotationType() {

    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnimation(animKConfig: MAnimKConfig, animation: Animation) {
        super.formatAnimation(animKConfig, animation)
        animation.apply {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.RESTART
        }
    }

    override fun formatAnimator(animKConfig: MAnimKConfig, animator: Animator) {
        super.formatAnimator(animKConfig, animator)
        (animator as ObjectAnimator).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
        }
    }
}