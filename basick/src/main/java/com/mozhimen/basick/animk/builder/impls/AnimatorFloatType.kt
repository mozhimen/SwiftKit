package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AnimatorNumType
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
class AnimatorFloatType : BaseAnimatorType<AnimatorFloatType, Float>() {
    protected var _startF = 0f
    protected var _endF = 1f

    fun setFloat(startF: Float, endF: Float): AnimatorFloatType {
        _startF = startF
        _endF = endF
        return this
    }

    override fun buildAnim(animKConfig: MAnimKConfig): Animator {
        val animator = ObjectAnimator.ofFloat(_startF, _endF)
        formatAnim(animKConfig, animator)
        return animator
    }
}