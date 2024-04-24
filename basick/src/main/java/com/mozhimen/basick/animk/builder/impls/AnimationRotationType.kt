package com.mozhimen.basick.animk.builder.impls

import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimationType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AnimationRotationType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
open class AnimationRotationType : BaseAnimationType<AnimationRotationType>() {
    private var _from = 0f
    private var _to = 360f

    init {
        setPivot(0.5f, 0.5f)
    }

    open fun rotate(@FloatRange(from = 0.0, to = 360.0) from: Float, @FloatRange(from = 0.0, to = 360.0) to: Float): AnimationRotationType {
        this._from = from
        this._to = to
        return this
    }

    override fun buildAnim(animKConfig: MAnimKConfig): Animation {
        val rotateAnimation = RotateAnimation(_from, _to, Animation.RELATIVE_TO_SELF, _pivotX, Animation.RELATIVE_TO_SELF, _pivotY)
        formatAnim(animKConfig, rotateAnimation)
        return rotateAnimation
    }

    //////////////////////////////////////////////////////////////

    companion object {
        val CLOCKWISE_90 = AnimationRotationType().rotate(0f, 90f)

        val CLOCKWISE_180 = AnimationRotationType().rotate(0f, 180f)

        val CLOCKWISE_360 = AnimationRotationType().rotate(0f, 360f)

        val ANTICLOCKWISE_90 = AnimationRotationType().rotate(90f, 0f)

        val ANTICLOCKWISE_180 = AnimationRotationType().rotate(180f, 0f)

        val ANTICLOCKWISE_360 = AnimationRotationType().rotate(360f, 0f)
    }
}