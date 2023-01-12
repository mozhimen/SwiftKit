package com.mozhimen.basick.animk.builders.bases

import android.animation.Animator
import android.view.animation.Animation
import com.mozhimen.basick.animk.builders.commons.IAnimationType
import com.mozhimen.basick.animk.builders.commons.IAnimatorType
import com.mozhimen.basick.animk.builders.mos.AnimKConfig

/**
 * @ClassName BaseAnimKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 17:46
 * @Version 1.0
 */
abstract class BaseAnimKType<T> : BaseType<T>(), IAnimatorType, IAnimationType {
    override fun formatAnimation(animKConfig: AnimKConfig, animation: Animation) {
        super.formatAnimation(animKConfig, animation)
        animation.interpolator = _interpolator ?: animKConfig.interpolator
    }

    override fun formatAnimator(animKConfig: AnimKConfig, animator: Animator) {
        super.formatAnimator(animKConfig, animator)
        animator.interpolator = _interpolator ?: animKConfig.interpolator
    }
}