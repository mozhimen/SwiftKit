package com.mozhimen.basick.animk.builder.helpers

import android.animation.Animator
import android.animation.AnimatorSet
import androidx.core.util.forEach
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorBuilder
import com.mozhimen.basick.animk.builder.commons.IAnimatorCreateListener

/**
 * @ClassName AnimatorBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:38
 * @Version 1.0
 */
class AnimatorBuilder : BaseAnimatorBuilder<AnimatorBuilder>() {
    fun build(listener: IAnimatorCreateListener? = null): Animator {
        val animatorSet = AnimatorSet()
        _types.forEach { _, value ->
            val childAnimator = value.buildAnimator(_animKConfig)
            listener?.onAnimatorCreated(childAnimator)
            animatorSet.playTogether(childAnimator)
        }
        listener?.onAnimatorCreateFinish(animatorSet)
        return animatorSet
    }
}