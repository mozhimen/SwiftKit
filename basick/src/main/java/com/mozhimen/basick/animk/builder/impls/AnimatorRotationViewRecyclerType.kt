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
class AnimatorRotationViewRecyclerType : AnimatorRotationViewType() {

    init {
        setInterpolator(LinearInterpolator())
    }

    override fun format(animKConfig: MAnimKConfig, anim: Animator) {
        super.format(animKConfig, anim)
        if (anim is ObjectAnimator){
            anim.apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.RESTART
            }
        }
    }
}