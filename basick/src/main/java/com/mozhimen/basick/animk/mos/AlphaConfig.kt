package com.mozhimen.basick.animk.mos

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.commons.IAnimKConfig

/**
 * @ClassName AlphaConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:46
 * @Version 1.0
 */
open class AlphaConfig : IAnimKConfig<AlphaConfig> {
    var alphaFrom = 0f
    var alphaTo = 0f
    var changed = false

    override fun resetInternal() {
        alphaFrom = 0f
        alphaTo = 1f
        changed = false
    }

    constructor() : super(false, false) {
        resetInternal()
    }

    constructor(resetParent: Boolean, resetInternal: Boolean) : super(resetParent, resetInternal) {
        resetInternal()
    }

    fun from(@FloatRange(from = 0.0, to = 1.0) from: Float): AlphaConfig {
        alphaFrom = from
        changed = true
        return this
    }

    fun to(@FloatRange(from = 0.0, to = 1.0) to: Float): AlphaConfig {
        alphaTo = to
        changed = true
        return this
    }

    fun from(from: Int): AlphaConfig {
        alphaFrom = (Math.max(0, Math.min(255, from)) / 255).toFloat() + 0.5f
        changed = true
        return this
    }

    fun to(to: Int): AlphaConfig {
        alphaFrom = (Math.max(0, Math.min(255, to)) / 255).toFloat() + 0.5f
        changed = true
        return this
    }

    override fun toString(): String {
        return "AlphaConfig{" +
                "alphaFrom=" + alphaFrom +
                ", alphaTo=" + alphaTo +
                '}'
    }

    override fun buildAnimation(isRevert: Boolean): Animation {
        val animation = AlphaAnimation(
            if (isRevert && !changed) alphaTo else alphaFrom,
            if (isRevert && !changed) alphaFrom else alphaTo
        )
        deploy(animation)
        return animation
    }

    override fun buildAnimator(isRevert: Boolean): Animator {
        val animator: Animator = ObjectAnimator.ofFloat(
            null,
            View.ALPHA,
            if (isRevert && !changed) alphaTo else alphaFrom,
            if (isRevert && !changed) alphaFrom else alphaTo
        )
        deploy(animator)
        return animator
    }

    companion object {
        //------------------default
        val IN: AlphaConfig = object : AlphaConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(0f)
                to(1f)
            }
        }
        val OUT: AlphaConfig = object : AlphaConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(1f)
                to(0f)
            }
        }
    }
}