package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName DrawableAlphaType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
open class AnimatorAlpha255Type : BaseAnimatorType<AnimatorAlpha255Type, Int>() {
    protected var _alphaFrom = 0f
    protected var _alphaTo = 1f

    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) fromAlpha: Float, @FloatRange(from = 0.0, to = 1.0) toAlpha: Float): AnimatorAlpha255Type {
        _alphaFrom = fromAlpha
        _alphaTo = toAlpha
        return this
    }

    fun show(): AnimatorAlpha255Type {
        _alphaFrom = 0f
        _alphaTo = 1f
        return this
    }

    fun hide(): AnimatorAlpha255Type {
        _alphaFrom = 1f
        _alphaTo = 0f
        return this
    }

    override fun buildAnim(animKConfig: MAnimKConfig): Animator {
        val animator = ObjectAnimator.ofInt(_alphaFrom.toInt() * 255, _alphaTo.toInt() * 255)
        formatAnim(animKConfig, animator)
        return animator
    }

    //////////////////////////////////////////////////////

    companion object {
        val SHOW: AnimatorAlpha255Type = AnimatorAlpha255Type().show()

        val HIDE: AnimatorAlpha255Type = AnimatorAlpha255Type().hide()
    }
}