package com.mozhimen.basick.animk.builder.commons

import android.animation.Animator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName IAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 17:31
 * @Version 1.0
 */
interface IAnimatorType : IUtilK {
    var _animator: Animator

    fun buildAnimator(animKConfig: MAnimKConfig): Animator

    fun formatAnimator(animKConfig: MAnimKConfig, animator: Animator) {
        animator.duration = animKConfig.duration
    }
}