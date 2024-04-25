package com.mozhimen.basick.animk.builder.impls

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AnimationScaleRecyclerType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
class AnimationScaleRecyclerType : AnimationScaleType() {
    init {
        setInterpolator(LinearInterpolator())
        scale(1f, 1f)
        hide()
    }

    override fun format(animKConfig: MAnimKConfig, animation: Animation) {
        super.format(animKConfig, animation)
        animation.apply {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
    }
}