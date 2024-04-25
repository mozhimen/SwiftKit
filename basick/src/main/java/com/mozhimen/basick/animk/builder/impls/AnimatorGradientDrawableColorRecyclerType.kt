package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName GradientDrawableColorRecyclerAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 19:21
 * @Version 1.0
 */
class AnimatorGradientDrawableColorRecyclerType: AnimatorGradientDrawableColorType() {
    init {
        setInterpolator(LinearInterpolator())
    }

    override fun format(animKConfig: MAnimKConfig, anim: Animator) {
        super.format(animKConfig, anim)
        if (anim is ObjectAnimator){
            anim.apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }
        }
    }
}