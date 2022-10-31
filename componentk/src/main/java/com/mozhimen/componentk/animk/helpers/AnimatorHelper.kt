package com.mozhimen.componentk.animk.helpers

import android.animation.*
import android.graphics.drawable.ColorDrawable
import androidx.core.view.ViewCompat
import com.mozhimen.componentk.animk.mos.MAnimator

object AnimatorHelper {

    /**
     * 开始闪动:interpolator: Interpolator = AccelerateDecelerateInterpolator(),
     * @param animatorMo AnimatorMo
     * @param startAlpha Float
     * @param endAlpha Float
     * @return Animator
     */
    @JvmStatic
    fun flash(
        animatorMo: MAnimator,
        startAlpha: Float = 1f,
        endAlpha: Float = 0f
    ): Animator =
        genAnimator(animatorMo, ObjectAnimator.ofFloat(animatorMo.view, "alpha", startAlpha, endAlpha, startAlpha))

    /**
     * 开始扩大然后缩小.像波浪一样:
     * interpolator: Interpolator = AccelerateDecelerateInterpolator(),
     * @param animatorMo AnimatorMo
     * @param ratio Float
     */
    @JvmStatic
    fun waver(
        animatorMo: MAnimator,
        ratio: Float = 1.1f,
    ): Animator {
        animatorMo.view.pivotX = animatorMo.view.width / 2f
        animatorMo.view.pivotY = animatorMo.view.height / 2f
        val objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, ratio, 1.0f)
        val objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, ratio, 1.0f)
        return genAnimator(animatorMo, ObjectAnimator.ofPropertyValuesHolder(animatorMo.view, objectAnimatorScaleX, objectAnimatorScaleY))
    }

    /**
     * 背景变换:interpolator: Interpolator = AccelerateDecelerateInterpolator(),
     * @param animatorMo AnimatorMo
     * @param colorStart Int
     * @param colorEnd Int
     * @return Animator
     */
    @JvmStatic
    fun bgBetweenColors(
        animatorMo: MAnimator,
        colorStart: Int,
        colorEnd: Int,
    ): Animator {
        val colorDrawable = ColorDrawable(colorStart)
        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorStart, colorEnd, colorStart)
        colorAnimator.addUpdateListener { animator: ValueAnimator ->
            colorDrawable.color = (animator.animatedValue as Int)
            ViewCompat.setBackground(animatorMo.view, colorDrawable)
        }
        return genAnimator(animatorMo, colorAnimator)
    }

    /**
     * 背景透明度变换:interpolator: Interpolator = AccelerateDecelerateInterpolator(),
     * @param animatorMo AnimatorMo
     * @param alphaEnd Int
     * @return Animator
     */
    @JvmStatic
    fun bgAlphaFlash(
        animatorMo: MAnimator,
        alphaEnd: Int = 0
    ): Animator {
        val alphaDrawable = animatorMo.view.background
        val alphaAnimator = ValueAnimator.ofInt(255, alphaEnd, 255)
        alphaAnimator.addUpdateListener {
            alphaDrawable.alpha = it.animatedValue as Int
            ViewCompat.setBackground(animatorMo.view, alphaDrawable)
        }
        return genAnimator(animatorMo, alphaAnimator)
    }

    private fun <T> genAnimator(animatorMo: MAnimator, animator: T): T where T : ValueAnimator {
        animator.repeatCount = animatorMo.repeatCount
        animator.repeatMode = animatorMo.repeatMode
        animator.interpolator = animatorMo.interpolator
        animator.duration = animatorMo.duration
        animatorMo.listener?.let {
            animator.addListener(it)
        }
        return animator
    }
}