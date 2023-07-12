package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AlphaRecyclerType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/20 16:37
 * @Version 1.0
 */
class AnimKAlphaRecyclerType : AnimKAlphaType() {
    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnimation(animKConfig: MAnimKConfig, animation: Animation) {
        super.formatAnimation(animKConfig, animation)
        animation.apply {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
    }

    override fun formatAnimator(animKConfig: MAnimKConfig, animator: Animator) {
        super.formatAnimator(animKConfig, animator)
        (animator as ObjectAnimator).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
    }

    companion object {
        val FLASH: AnimKAlphaType = AnimKAlphaRecyclerType().hide()
    }
}