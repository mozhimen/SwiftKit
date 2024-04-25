package com.mozhimen.basick.animk.builder.impls

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName RotateRecyclerType
 * @Description 不断旋转
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
class AnimationRotationRecyclerType : AnimationRotationType() {

    init {
        setInterpolator(LinearInterpolator())
    }

    override fun format(animKConfig: MAnimKConfig, animation: Animation) {
        super.format(animKConfig, animation)
        animation.apply {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.RESTART
        }
    }
}