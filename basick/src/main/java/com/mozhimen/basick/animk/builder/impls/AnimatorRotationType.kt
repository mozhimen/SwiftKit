package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AnimatorRotationType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
open class AnimatorRotationType : AnimatorFloatType() {

    open fun rotate(@FloatRange(from = 0.0, to = 360.0) from: Float, @FloatRange(from = 0.0, to = 360.0) to: Float): AnimatorRotationType {
        setFloat(from, to)
        return this
    }

    //////////////////////////////////////////////////////////////

    companion object {
        val CLOCKWISE_90 = AnimatorRotationType().rotate(0f, 90f)

        val CLOCKWISE_180 = AnimatorRotationType().rotate(0f, 180f)

        val CLOCKWISE_360 = AnimatorRotationType().rotate(0f, 360f)

        val ANTICLOCKWISE_90 = AnimatorRotationType().rotate(90f, 0f)

        val ANTICLOCKWISE_180 = AnimatorRotationType().rotate(180f, 0f)

        val ANTICLOCKWISE_360 = AnimatorRotationType().rotate(360f, 0f)
    }
}