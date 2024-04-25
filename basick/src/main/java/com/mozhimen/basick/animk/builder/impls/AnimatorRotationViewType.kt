package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.commons.IAnimViewType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig
import java.lang.ref.WeakReference

/**
 * @ClassName AnimatorRotationType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
open class AnimatorRotationViewType : BaseAnimatorType<AnimatorRotationViewType, Float>(), IAnimViewType<AnimatorRotationViewType> {
    private var _from = 0f
    private var _to = 360f
    private var _viewRef: WeakReference<View>? = null

    init {
        setPivot(0.5f, 0.5f)
    }

    override fun setViewRef(view: View): AnimatorRotationViewType {
        _viewRef = WeakReference(view)
        return this
    }

    open fun rotate(@FloatRange(from = 0.0, to = 360.0) from: Float, @FloatRange(from = 0.0, to = 360.0) to: Float): AnimatorRotationViewType {
        this._from = from
        this._to = to
        return this
    }

    override fun build(animKConfig: MAnimKConfig): Animator {
        val animator = if (_viewRef?.get() != null) {
            ObjectAnimator.ofFloat(_viewRef?.get(), View.ROTATION, _from, _to)
        } else {
            ObjectAnimator.ofFloat(_from, _to)
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                val target = (animation as ObjectAnimator).target
                if (target is View) {
                    target.pivotX = target.width * _pivotX
                    target.pivotY = target.height * _pivotY
                }
            }
        })
        format(animKConfig, animator)
        return animator
    }

    //////////////////////////////////////////////////////////////

    companion object {
        val CLOCKWISE_90 = AnimatorRotationViewType().rotate(0f, 90f)

        val CLOCKWISE_180 = AnimatorRotationViewType().rotate(0f, 180f)

        val CLOCKWISE_360 = AnimatorRotationViewType().rotate(0f, 360f)

        val ANTICLOCKWISE_90 = AnimatorRotationViewType().rotate(90f, 0f)

        val ANTICLOCKWISE_180 = AnimatorRotationViewType().rotate(180f, 0f)

        val ANTICLOCKWISE_360 = AnimatorRotationViewType().rotate(360f, 0f)
    }
}