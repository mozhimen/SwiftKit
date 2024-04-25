package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.commons.IAnimViewType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig
import java.lang.ref.WeakReference

/**
 * @ClassName DrawableAlphaType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
open class AnimatorAlphaViewType : BaseAnimatorType<AnimatorAlphaViewType, Int>(), IAnimViewType<AnimatorAlphaViewType> {
    protected var _alphaFrom = 0f
    protected var _alphaTo = 1f
    protected var _viewRef: WeakReference<View>? = null

    override fun setViewRef(view: View): AnimatorAlphaViewType {
        _viewRef = WeakReference(view)
        return this
    }

    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) fromAlpha: Float, @FloatRange(from = 0.0, to = 1.0) toAlpha: Float): AnimatorAlphaViewType {
        _alphaFrom = fromAlpha
        _alphaTo = toAlpha
        return this
    }

    fun show(): AnimatorAlphaViewType {
        setAlpha(0f, 1f)
        return this
    }

    fun hide(): AnimatorAlphaViewType {
        setAlpha(1f, 0f)
        return this
    }

    override fun build(animKConfig: MAnimKConfig): Animator {
        val animator = if (_viewRef?.get() != null) {
            ObjectAnimator.ofFloat(_viewRef?.get(), View.ALPHA, _alphaFrom, _alphaTo)
        } else {
            ObjectAnimator.ofFloat(_alphaFrom, _alphaTo)
        }
        format(animKConfig, animator)
        return animator
    }

    //////////////////////////////////////////////////////

    companion object {
        val SHOW: AnimatorAlphaViewType = AnimatorAlphaViewType().show()

        val HIDE: AnimatorAlphaViewType = AnimatorAlphaViewType().hide()
    }
}