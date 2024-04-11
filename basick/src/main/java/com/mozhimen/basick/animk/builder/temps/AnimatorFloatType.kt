package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
class AnimatorFloatType : BaseAnimatorType<AnimatorFloatType>() {
    override lateinit var _animator: Animator
    protected var _startF = 0f
    protected var _endF = 1f
    protected var _animatorUpdateListener: IAnimatorUpdateListener<Float>? = null

    fun setFloat(startF: Float, endF: Float): AnimatorFloatType {
        _startF = startF
        _endF = endF
        return this
    }

    open fun addAnimatorUpdateListener(listener: IAnimatorUpdateListener<Float>): AnimatorFloatType {
        _animatorUpdateListener = listener
        return this
    }

    override fun buildAnimator(animKConfig: MAnimKConfig): Animator {
        _animator = ObjectAnimator.ofFloat(_startF, _endF)
        _animatorUpdateListener?.let {
            (_animator as ValueAnimator).addUpdateListener {
                _animatorUpdateListener!!.onChange(it.animatedValue as Float)
            }
        }
        formatAnimator(animKConfig, _animator)
        return _animator
    }
}