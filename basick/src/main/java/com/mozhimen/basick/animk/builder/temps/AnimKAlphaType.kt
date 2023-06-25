package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimKType
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName AlphaConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:46
 * @Version 1.0
 */
open class AnimKAlphaType : BaseAnimKType<AnimKAlphaType>() {
    protected var _alphaFrom = 0f
    protected var _alphaTo = 1f
    override lateinit var _animator: Animator

    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) fromAlpha: Float, @FloatRange(from = 0.0, to = 1.0) toAlpha: Float): AnimKAlphaType {
        _alphaFrom = fromAlpha
        _alphaTo = toAlpha
        return this
    }

    fun show(): AnimKAlphaType {
        _alphaFrom = 0f
        _alphaTo = 1f
        return this
    }

    fun hide(): AnimKAlphaType {
        _alphaFrom = 1f
        _alphaTo = 0f
        return this
    }

    override fun buildAnimation(animKConfig: AnimKConfig): Animation {
        val animation = AlphaAnimation(_alphaFrom, _alphaTo)
        formatAnimation(animKConfig, animation)
        return animation
    }

    override fun buildAnimator(animKConfig: AnimKConfig): Animator {
        _animator = ObjectAnimator.ofFloat(null, View.ALPHA, _alphaFrom, _alphaTo)
        formatAnimator(animKConfig, _animator)
        return _animator
    }

    companion object {
        val SHOW: AnimKAlphaType = AnimKAlphaType().show()

        val HIDE: AnimKAlphaType = AnimKAlphaType().hide()
    }
}