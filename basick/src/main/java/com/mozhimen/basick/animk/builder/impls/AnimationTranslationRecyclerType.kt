package com.mozhimen.basick.animk.builder.impls

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
class AnimationTranslationRecyclerType : AnimationTranslationType() {

    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnim(animKConfig: MAnimKConfig, animation: Animation) {
        super.formatAnim(animKConfig, animation)
        animation.apply {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
    }
}