package com.mozhimen.basick.animk.builder.bases

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import com.mozhimen.basick.animk.builder.commons.IAnimatorType
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig
import com.mozhimen.basick.animk.builder.temps.AnimatorAlphaType

/**
 * @ClassName BaseAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseAnimatorType<T> : BasePropertyType<T>(), IAnimatorType {
    protected var _animatorListenerAdapter: AnimatorListenerAdapter? = null

    open fun addAnimatorListener(listener: AnimatorListenerAdapter): BaseAnimatorType<T> {
        _animatorListenerAdapter = listener
        return this
    }

    override fun formatAnimator(animKConfig: MAnimKConfig, animator: Animator) {
        super.formatAnimator(animKConfig, animator)
        _animatorListenerAdapter?.let {
            (_animator as ObjectAnimator).addListener(it)
        }
        animator.interpolator = _interpolator ?: animKConfig.interpolator
    }
}