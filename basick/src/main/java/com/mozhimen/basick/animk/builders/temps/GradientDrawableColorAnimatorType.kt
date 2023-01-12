package com.mozhimen.basick.animk.builders.temps

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.mozhimen.basick.animk.builders.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builders.mos.AnimKConfig
import java.lang.Exception

/**
 * @ClassName DrawableColorAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 19:07
 * @Version 1.0
 */
open class GradientDrawableColorAnimatorType : BaseAnimatorType<GradientDrawableColorAnimatorType>() {
    private var _gradientDrawable: GradientDrawable? = null
    private var _colorStart: Int = Color.WHITE
    private var _colorEnd: Int = Color.BLACK
    override lateinit var _animator: Animator

    fun setGradientDrawable(gradientDrawable: GradientDrawable): GradientDrawableColorAnimatorType {
        _gradientDrawable = gradientDrawable
        return this
    }

    fun setColorRange(colorStart: Int, colorEnd: Int): GradientDrawableColorAnimatorType {
        _colorStart = colorStart
        _colorEnd = colorEnd
        return this
    }

    override fun buildAnimator(animKConfig: AnimKConfig): Animator {
        _gradientDrawable ?: throw Exception("you should set _drawable")
        _animator = ObjectAnimator.ofInt(_gradientDrawable!!, "color", _colorStart, _colorEnd)
        (_animator as ObjectAnimator).setEvaluator(ArgbEvaluator())
        formatAnimator(animKConfig, _animator)
        return _animator
    }
}