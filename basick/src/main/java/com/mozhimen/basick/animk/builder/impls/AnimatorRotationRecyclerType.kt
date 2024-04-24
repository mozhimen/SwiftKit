package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName RotateRecyclerType
 * @Description 不断旋转
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
class AnimatorRotationRecyclerType : AnimatorRotationType() {

    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnim(animKConfig: MAnimKConfig, anim: Animator) {
        super.formatAnim(animKConfig, anim)
        if (anim is ObjectAnimator){
            anim.apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.RESTART
            }
        }
    }
}