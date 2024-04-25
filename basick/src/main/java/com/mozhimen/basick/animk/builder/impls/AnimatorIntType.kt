package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AnimatorNumType
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
open class AnimatorIntType : BaseAnimatorType<AnimatorIntType, Int>() {
    protected var _start = 0
    protected var _end = 1

    fun setInt(start: Int, end: Int): AnimatorIntType {
        _start = start
        _end = end
        return this
    }

    override fun buildAnim(animKConfig: MAnimKConfig): Animator {
        val animator = ObjectAnimator.ofInt(_start, _end)
        formatAnim(animKConfig, animator)
        return animator
    }
}