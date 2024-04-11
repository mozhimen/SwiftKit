package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName ColorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
class AnimatorColorRecyclerType : AnimatorColorType() {
    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnimator(animKConfig: MAnimKConfig, animator: Animator) {
        super.formatAnimator(animKConfig, animator)
        (animator as ObjectAnimator).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
    }
}