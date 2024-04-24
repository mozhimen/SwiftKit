package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName DrawableAlphaType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
open class AnimatorAlphaViewType : BaseAnimatorType<AnimatorAlphaViewType, Int>() {
    protected var _alphaFrom = 0f
    protected var _alphaTo = 1f

    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) fromAlpha: Float, @FloatRange(from = 0.0, to = 1.0) toAlpha: Float): AnimatorAlphaViewType {
        _alphaFrom = fromAlpha
        _alphaTo = toAlpha
        return this
    }

    fun show(): AnimatorAlphaViewType {
        _alphaFrom = 0f
        _alphaTo = 1f
        return this
    }

    fun hide(): AnimatorAlphaViewType {
        _alphaFrom = 1f
        _alphaTo = 0f
        return this
    }

    override fun buildAnim(animKConfig: MAnimKConfig): Animator {
        val animator = ObjectAnimator.ofFloat(null, View.ALPHA, _alphaFrom, _alphaTo)
        formatAnim(animKConfig, animator)
        return animator
    }

    //////////////////////////////////////////////////////

    companion object {
        val SHOW: AnimatorAlphaViewType = AnimatorAlphaViewType().show()

        val HIDE: AnimatorAlphaViewType = AnimatorAlphaViewType().hide()
    }
}