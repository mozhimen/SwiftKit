package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName TranslationRecyclerType
 * @Description 来回移动
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
class AnimKTranslationRecyclerType : AnimKTranslationType() {

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
        (animator as AnimatorSet).childAnimations.forEach {
            (it as ObjectAnimator).apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }
        }
    }
}