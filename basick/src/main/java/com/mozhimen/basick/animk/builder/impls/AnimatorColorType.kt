package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName DrawableColorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
open class AnimatorColorType : BaseAnimatorType<AnimatorColorType, Int>() {
    protected var _colorStart: Int = Color.WHITE
    protected var _colorEnd: Int = Color.BLACK

    fun setColorRange(colorStart: Int, colorEnd: Int): AnimatorColorType {
        _colorStart = colorStart
        _colorEnd = colorEnd
        return this
    }

    override fun build(animKConfig: MAnimKConfig): Animator {
        val animator = ObjectAnimator.ofObject(ArgbEvaluator(), _colorStart, _colorEnd)
        format(animKConfig, animator)
        return animator
    }
}