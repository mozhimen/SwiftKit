package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimKType
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName RotationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:01
 * @Version 1.0
 */
open class AnimKRotationType : BaseAnimKType<AnimKRotationType>() {
    private var _from = 0f
    private var _to = 360f
    override lateinit var _animator: Animator

    init {
        setPivot(0.5f, 0.5f)
    }

    open fun rotate(@FloatRange(from = 0.0, to = 360.0) from: Float, @FloatRange(from = 0.0, to = 360.0) to: Float): AnimKRotationType {
        this._from = from
        this._to = to
        return this
    }

    override fun buildAnimation(animKConfig: AnimKConfig): Animation {
        val rotateAnimation = RotateAnimation(_from, _to, Animation.RELATIVE_TO_SELF, _pivotX, Animation.RELATIVE_TO_SELF, _pivotY)
        formatAnimation(animKConfig, rotateAnimation)
        return rotateAnimation
    }

    override fun buildAnimator(animKConfig: AnimKConfig): Animator {
        _animator = ObjectAnimator.ofFloat(null, View.ROTATION, _from, _to)
        _animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                val target = (animation as ObjectAnimator).target
                if (target is View) {
                    target.pivotX = target.width * _pivotX
                    target.pivotY = target.height * _pivotY
                }
            }
        })
        formatAnimator(animKConfig, _animator)
        return _animator
    }

    companion object {
        val CLOCKWISE_360 = AnimKRotationType().rotate(0f, 360f)

        val ANTICLOCKWISE_360 = AnimKRotationType().rotate(360f, 0f)

        val CLOCKWISE_90 = AnimKRotationType().rotate(0f, 90f)

        val ANTICLOCKWISE_90 = AnimKRotationType().rotate(90f, 0f)

        val CLOCKWISE_180 = AnimKRotationType().rotate(0f, 180f)

        val ANTICLOCKWISE_180 = AnimKRotationType().rotate(180f, 0f)
    }
}
