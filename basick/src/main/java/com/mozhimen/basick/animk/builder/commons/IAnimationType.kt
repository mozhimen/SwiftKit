package com.mozhimen.basick.animk.builder.commons

import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName IAnimationType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
interface IAnimationType : IAnimType<Animation> {
    override fun buildAnim(animKConfig: MAnimKConfig): Animation

    override fun formatAnim(animKConfig: MAnimKConfig, anim: Animation) {
        anim.fillBefore = animKConfig.fillBefore
        anim.fillAfter = animKConfig.fillAfter
        anim.duration = animKConfig.duration
    }
}