package com.mozhimen.basick.animk.builder.impls

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
open class AnimatorGradientDrawableColorType : BaseAnimatorType<AnimatorGradientDrawableColorType, Int>() {
    private var _gradientDrawable: GradientDrawable? = null
    private var _colors: IntArray = intArrayOf(Color.WHITE, Color.BLACK)

    fun setGradientDrawable(gradientDrawable: GradientDrawable): AnimatorGradientDrawableColorType {
        _gradientDrawable = gradientDrawable
        return this
    }

    fun setColors(@ColorInt vararg intColor: Int): AnimatorGradientDrawableColorType {
        _colors = intColor
        return this
    }

    override fun build(animKConfig: MAnimKConfig): Animator {
        requireNotNull(_gradientDrawable) { "$TAG you should set _drawable" }
        val animator: ObjectAnimator = ObjectAnimator.ofInt(_gradientDrawable!!, "color", *_colors)
        animator.setEvaluator(ArgbEvaluator())
        format(animKConfig, animator)
        return animator
    }
}