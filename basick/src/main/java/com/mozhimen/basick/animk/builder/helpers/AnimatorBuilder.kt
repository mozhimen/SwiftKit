package com.mozhimen.basick.animk.builder.helpers

import android.animation.Animator
import android.animation.AnimatorSet
import androidx.core.util.forEach
import com.mozhimen.basick.animk.builder.bases.BaseAnimatorBuilder
import com.mozhimen.basick.animk.builder.commons.IAnimCreateListener

/**
 * @ClassName AnimatorBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:38
 * @Version 1.0
 */
class AnimatorBuilder : BaseAnimatorBuilder<AnimatorBuilder>() {
    override fun build(): Animator {
        return build(null)
    }

    override fun build(listener: IAnimCreateListener<Animator, AnimatorSet>?): Animator {
        val animatorSet = AnimatorSet()
        _types.forEach { _, type ->
            val childAnim = type.build(_animKConfig)
            listener?.onAnimCreated(childAnim)
            animatorSet.playTogether(childAnim)
        }
        listener?.onAnimSetCreated(animatorSet)
        return animatorSet
    }
}