package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName DrawableColorAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 19:07
 * @Version 1.0
 */
open class AnimatorGradientDrawableColorType : BaseAnimatorType<AnimatorGradientDrawableColorType>() {
    private var _gradientDrawable: GradientDrawable? = null
    private var _colors: IntArray = intArrayOf(Color.WHITE, Color.BLACK)
    override lateinit var _animator: Animator

    fun setGradientDrawable(gradientDrawable: GradientDrawable): AnimatorGradientDrawableColorType {
        _gradientDrawable = gradientDrawable
        return this
    }

    fun setColors(@ColorInt vararg colorInt: Int): AnimatorGradientDrawableColorType {
        _colors = colorInt
        return this
    }

    override fun buildAnimator(animKConfig: MAnimKConfig): Animator {
        requireNotNull(_gradientDrawable) { "$TAG you should set _drawable" }
        _animator = ObjectAnimator.ofInt(_gradientDrawable!!, "color", *_colors)
        (_animator as ObjectAnimator).setEvaluator(ArgbEvaluator())
        formatAnimator(animKConfig, _animator)
        return _animator
    }
}