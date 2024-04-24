package com.mozhimen.basick.animk.builder.bases

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import com.mozhimen.basick.animk.builder.commons.IAnimatorType
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress

/**
 * @ClassName BaseAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
@Suppress(CSuppress.UNCHECKED_CAST)
abstract class BaseAnimatorType<TYPE, UPDATE_VALUE> : BaseProperty<TYPE>(), IAnimatorType {

    protected var _animatorListenerAdapter: AnimatorListenerAdapter? = null
    protected var _animatorUpdateListener: IAnimatorUpdateListener<UPDATE_VALUE>? = null

    fun addAnimatorListener(listener: AnimatorListenerAdapter): TYPE {
        _animatorListenerAdapter = listener
        return this as TYPE
    }

    fun addAnimatorUpdateListener(listener: IAnimatorUpdateListener<UPDATE_VALUE>): TYPE {
        _animatorUpdateListener = listener
        return this as TYPE
    }

    override fun formatAnim(animKConfig: MAnimKConfig, anim: Animator) {
        super.formatAnim(animKConfig, anim)
        _animatorListenerAdapter?.let {
            (anim as? ObjectAnimator?)?.addListener(it)
        }
        _animatorUpdateListener?.let {
            if (anim is ValueAnimator){
                anim.addUpdateListener {
                    _animatorUpdateListener!!.onChange(it.animatedValue as? UPDATE_VALUE?)
                }
            }
        }
        anim.interpolator = _interpolator ?: animKConfig.interpolator
    }
}