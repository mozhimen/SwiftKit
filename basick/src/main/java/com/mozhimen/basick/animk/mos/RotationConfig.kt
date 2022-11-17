package com.mozhimen.basick.animk.mos

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.mozhimen.basick.animk.commons.IAnimKConfig

/**
 * @ClassName RotationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:01
 * @Version 1.0
 */
class RotationConfig : IAnimKConfig<RotationConfig> {
    var from = 0f
    var to = 0f
    override fun resetInternal() {
        to = 0f
        from = to
        pivot(.5f, .5f)
    }

    constructor() : super(false, false) {
        resetInternal()
    }

    internal constructor(resetParent: Boolean, resetInternal: Boolean) : super(resetParent, resetInternal) {
        resetInternal()
    }

    fun from(from: Float): RotationConfig {
        this.from = from
        return this
    }

    fun to(to: Float): RotationConfig {
        this.to = to
        return this
    }

    override fun buildAnimation(isRevert: Boolean): Animation {
        val rotateAnimation = RotateAnimation(
            from,
            to,
            Animation.RELATIVE_TO_SELF,
            pivotX,
            Animation.RELATIVE_TO_SELF,
            pivotY
        )
        deploy(rotateAnimation)
        return rotateAnimation
    }

    override fun buildAnimator(isRevert: Boolean): Animator {
        val rotate: Animator = ObjectAnimator.ofFloat(null, View.ROTATION, from, to)
        rotate.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                val target = (animation as ObjectAnimator).target
                if (target is View) {
                    target.pivotX = target.width * pivotX
                    target.pivotY = target.height * pivotY
                }
            }
        })
        deploy(rotate)
        return rotate
    }
}
