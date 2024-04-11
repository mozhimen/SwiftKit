package com.mozhimen.basick.animk.builder.bases

import android.animation.Animator
import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.commons.IAnimationType
import com.mozhimen.basick.animk.builder.commons.IAnimatorType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName BaseAnimKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseAnimKType<T> : BasePropertyType<T>(), IAnimatorType, IAnimationType {
    override fun formatAnimation(animKConfig: MAnimKConfig, animation: Animation) {
        super.formatAnimation(animKConfig, animation)
        animation.interpolator = _interpolator ?: animKConfig.interpolator
    }

    override fun formatAnimator(animKConfig: MAnimKConfig, animator: Animator) {
        super.formatAnimator(animKConfig, animator)
        animator.interpolator = _interpolator ?: animKConfig.interpolator
    }
}