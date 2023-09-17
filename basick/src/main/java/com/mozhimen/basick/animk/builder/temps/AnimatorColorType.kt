package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName DrawableColorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 16:36
 * @Version 1.0
 */
open class AnimatorColorType : BaseAnimatorType<AnimatorColorType>() {
    override lateinit var _animator: Animator
    protected var _colorStart: Int = Color.WHITE
    protected var _colorEnd: Int = Color.BLACK
    protected var _animatorUpdateListener: IAnimatorUpdateListener<Int>? = null

    fun setColorRange(colorStart: Int, colorEnd: Int): AnimatorColorType {
        _colorStart = colorStart
        _colorEnd = colorEnd
        return this
    }

    open fun addAnimatorUpdateListener(listener: IAnimatorUpdateListener<Int>): AnimatorColorType {
        _animatorUpdateListener = listener
        return this
    }

    override fun buildAnimator(animKConfig: MAnimKConfig): Animator {
        _animator = ObjectAnimator.ofObject(ArgbEvaluator(), _colorStart, _colorEnd)
        _animatorUpdateListener?.let {
            (_animator as ValueAnimator).addUpdateListener {
                _animatorUpdateListener!!.onChange(it.animatedValue as Int)
            }
        }
        formatAnimator(animKConfig, _animator)
        return _animator
    }
}