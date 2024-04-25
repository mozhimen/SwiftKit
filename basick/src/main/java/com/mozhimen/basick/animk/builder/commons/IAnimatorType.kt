package com.mozhimen.basick.animk.builder.commons

import android.animation.Animator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName IAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
interface IAnimatorType : IAnimType<Animator> {
    override fun format(animKConfig: MAnimKConfig, anim: Animator) {
        anim.duration = animKConfig.duration
    }
}