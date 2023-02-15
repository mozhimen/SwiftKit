package com.mozhimen.basick.animk.builder.commons

import android.animation.Animator
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName IAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 17:31
 * @Version 1.0
 */
interface IAnimatorType {
    var _animator: Animator

    fun buildAnimator(animKConfig: AnimKConfig): Animator

    fun formatAnimator(animKConfig: AnimKConfig, animator: Animator) {
        animator.duration = animKConfig.duration
    }
}