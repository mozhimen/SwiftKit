package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.ValueAnimator
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName DrawableAlphaType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/20 17:11
 * @Version 1.0
 */
open class DrawableAlphaRecyclerType : AlphaRecyclerType() {
    private lateinit var _animator: ValueAnimator

    open fun addAnimatorUpdateListener(listener: IAnimatorUpdateListener): DrawableAlphaRecyclerType {
        _animator.addUpdateListener {
            listener.onChange(it.animatedValue as Int)
        }
        return this
    }

    override fun buildAnimator(animKConfig: AnimKConfig): Animator {
        _animator = ValueAnimator.ofInt(_alphaFrom.toInt() * 255, _alphaTo.toInt() * 255)
        formatAnimator(animKConfig, _animator)
        return _animator
    }
}