package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName TranslationRecyclerType
 * @Description 来回移动
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
class AnimatorTranslationRecyclerType : AnimatorTranslationType() {

    init {
        setInterpolator(LinearInterpolator())
    }

    override fun format(animKConfig: MAnimKConfig, anim: Animator) {
        super.format(animKConfig, anim)
        if (anim is AnimatorSet){
            anim.childAnimations.forEach {
                (it as ObjectAnimator).apply {
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.REVERSE
                }
            }
        }
    }
}