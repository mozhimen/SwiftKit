package com.mozhimen.basick.animk.builder.commons

import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName IAnimationType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
interface IAnimationType {
    fun buildAnimation(animKConfig: MAnimKConfig): Animation

    fun formatAnimation(animKConfig: MAnimKConfig, animation: Animation) {
        animation.fillBefore = animKConfig.fillBefore
        animation.fillAfter = animKConfig.fillAfter
        animation.duration = animKConfig.duration
    }
}