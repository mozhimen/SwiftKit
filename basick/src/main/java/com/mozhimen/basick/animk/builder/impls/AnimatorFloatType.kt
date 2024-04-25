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
open class AnimatorFloatType : BaseAnimatorType<AnimatorFloatType, Float>() {
    protected var _startF = 0f
    protected var _endF = 1f

    fun setFloat(startF: Float, endF: Float): AnimatorFloatType {
        _startF = startF
        _endF = endF
        return this
    }

    override fun build(animKConfig: MAnimKConfig): Animator {
        val animator = ObjectAnimator.ofFloat(_startF, _endF)
        format(animKConfig, animator)
        return animator
    }
}