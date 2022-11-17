package com.mozhimen.basick.animk.helpers

import android.animation.Animator
import android.animation.AnimatorSet
import com.mozhimen.basick.animk.commons.IAnimK
import com.mozhimen.basick.animk.commons.IAnimatorCreateListener

/**
 * @ClassName AnimatorBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:38
 * @Version 1.0
 */
class AnimatorBuilder : IAnimK<AnimatorBuilder>() {
    @JvmOverloads
    fun show(listener: IAnimatorCreateListener? = null): Animator {
        val animatorSet = AnimatorSet()
        if (configs != null) {
            for (i in 0 until configs!!.size()) {
                val config = configs!!.valueAt(i)
                val childAnimator = config.`$buildAnimator`(false)
                listener?.onAnimatorCreated(childAnimator)
                animatorSet.playTogether(childAnimator)
            }
            listener?.onAnimatorCreateFinish(animatorSet)
        }
        return animatorSet
    }

    @JvmOverloads
    fun dismiss(listener: IAnimatorCreateListener? = null): Animator {
        val animatorSet = AnimatorSet()
        if (configs != null) {
            for (i in 0 until configs!!.size()) {
                val config = configs!!.valueAt(i)
                val childAnimator = config.`$buildAnimator`(true)
                listener?.onAnimatorCreated(childAnimator)
                animatorSet.playTogether(childAnimator)
            }
            listener?.onAnimatorCreateFinish(animatorSet)
        }
        return animatorSet
    }
}