package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName ColorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/20 17:34
 * @Version 1.0
 */
class DrawableColorRecyclerType : DrawableAlphaRecyclerType() {
    private var _colorStart: Int = Color.WHITE
    private var _colorEnd: Int = Color.BLACK
    private lateinit var _animator: ValueAnimator

    override fun addAnimatorUpdateListener(listener: IAnimatorUpdateListener): DrawableColorRecyclerType {
        _animator.addUpdateListener {
            listener.onChange(it.animatedValue as Int)
        }
        return this
    }

    fun setColorRange(colorStart: Int, colorEnd: Int): DrawableColorRecyclerType {
        _colorStart = colorStart
        _colorEnd = colorEnd
        return this
    }

    override fun buildAnimator(animKConfig: AnimKConfig): Animator {
        _animator = ValueAnimator.ofObject(ArgbEvaluator(), _colorStart, _colorEnd)
        formatAnimator(animKConfig, _animator)
        return _animator
    }
}