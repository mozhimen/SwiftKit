package com.mozhimen.basick.animk.builder.impls

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AnimationAlphaRecyclerType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
class AnimationAlphaRecyclerType : AnimationAlphaType(){
    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnim(animKConfig: MAnimKConfig, anim: Animation) {
        super.formatAnim(animKConfig, anim)
        anim.apply {
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
    }

    companion object {
        val FLASH: AnimationAlphaType = AnimationAlphaRecyclerType().hide()
    }
}