package com.mozhimen.basick.animk.builder.impls

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName AlphaRecyclerAnimatorType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 18:38
 * @Version 1.0
 */
class AnimatorAlphaViewRecyclerType : AnimatorAlphaViewType() {
    init {
        setInterpolator(LinearInterpolator())
    }

    override fun formatAnim(animKConfig: MAnimKConfig, anim: Animator) {
        super.formatAnim(animKConfig, anim)
        if(anim is ObjectAnimator){
            anim.apply {
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }
        }
    }

    //////////////////////////////////////////////////////////

    companion object {
        val FLASH: AnimatorAlphaViewType = AnimatorAlphaViewRecyclerType().hide()
    }
}