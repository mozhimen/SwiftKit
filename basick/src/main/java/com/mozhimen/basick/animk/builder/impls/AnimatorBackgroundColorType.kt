package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig


/**
 * @ClassName BackgroundColorAnimatorType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
class AnimatorBackgroundColorType : BaseAnimatorType<AnimatorBackgroundColorType, Nothing>() {
    private var _colors: IntArray = intArrayOf(Color.WHITE, Color.BLACK)
    private var _view: View? = null

    fun setColors(@ColorInt vararg intColor: Int): AnimatorBackgroundColorType {
        _colors = intColor
        return this
    }

    fun setView(view: View): AnimatorBackgroundColorType {
        _view = view
        return this
    }

    override fun build(animKConfig: MAnimKConfig): Animator {
        requireNotNull(_view) { "$TAG you should set _view" }
        val animator = ObjectAnimator.ofInt(_view!!, "backgroundColor", *_colors)
        if (animator is ObjectAnimator) {
            animator.setEvaluator(ArgbEvaluator())
        }
        format(animKConfig, animator)
        return animator
    }
}