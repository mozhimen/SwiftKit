package com.mozhimen.basick.animk.builders.temps

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import com.mozhimen.basick.animk.builders.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builders.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builders.mos.AnimKConfig

/**
 * @ClassName DrawableColorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 16:36
 * @Version 1.0
 */
open class ColorAnimatorType : BaseAnimatorType<ColorAnimatorType>() {
    private var _colorStart: Int = Color.WHITE
    private var _colorEnd: Int = Color.BLACK
    override lateinit var _animator: Animator

    fun setColorRange(colorStart: Int, colorEnd: Int): ColorAnimatorType {
        _colorStart = colorStart
        _colorEnd = colorEnd
        return this
    }

    fun addAnimatorUpdateListener(listener: IAnimatorUpdateListener): ColorAnimatorType {
        (_animator as ObjectAnimator).addUpdateListener {
            listener.onChange(it.animatedValue as Int)
        }
        return this
    }

    override fun buildAnimator(animKConfig: AnimKConfig): Animator {
        _animator = ObjectAnimator.ofObject(ArgbEvaluator(), _colorStart, _colorEnd)
        formatAnimator(animKConfig, _animator)
        return _animator
    }
}