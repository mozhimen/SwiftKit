package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName ScaleRecyclerType
 * @Description 开始扩大然后缩小.像波浪一样
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
class AnimatorScaleRecyclerType : AnimatorScaleType() {
    init {
        setInterpolator(LinearInterpolator())
        scale(1f, 1f)
        hide()
    }

    override fun format(animKConfig: MAnimKConfig, anim: Animator) {
        super.format(animKConfig, anim)
        (anim as AnimatorSet).childAnimations.forEach {
            (it as ObjectAnimator).apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }
        }
    }
}