package com.mozhimen.basick.animk.helpers

import android.view.animation.Animation
import android.view.animation.AnimationSet
import com.mozhimen.basick.animk.commons.IAnimK
import com.mozhimen.basick.animk.commons.IAnimationCreateListener

/**
 * @ClassName AnimationBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:38
 * @Version 1.0
 */
class AnimationBuilder : IAnimK<AnimationBuilder>() {
    @JvmOverloads
    fun show(listener: IAnimationCreateListener? = null): Animation {
        val animationSet = AnimationSet(false)
        if (configs != null) {
            for (i in 0 until configs!!.size()) {
                val config = configs!!.valueAt(i)
                val childAnim = config.`$buildAnimation`(false)
                if (childAnim.isFillEnabled) {
                    animationSet.isFillEnabled = true
                }
                if (childAnim.fillBefore) {
                    animationSet.fillBefore = true
                }
                if (childAnim.fillAfter) {
                    animationSet.fillAfter = true
                }
                listener?.onAnimationCreated(childAnim)
                animationSet.addAnimation(childAnim)
            }
            listener?.onAnimationCreateFinish(animationSet)
        }
        return animationSet
    }

    @JvmOverloads
    fun dismiss(listener: IAnimationCreateListener? = null): Animation {
        val animationSet = AnimationSet(false)
        if (configs != null) {
            for (i in 0 until configs!!.size()) {
                val config = configs!!.valueAt(i)
                val childAnim = config.`$buildAnimation`(true)
                if (childAnim.isFillEnabled) {
                    animationSet.isFillEnabled = true
                }
                if (childAnim.fillBefore) {
                    animationSet.fillBefore = true
                }
                if (childAnim.fillAfter) {
                    animationSet.fillAfter = true
                }
                listener?.onAnimationCreated(childAnim)
                animationSet.addAnimation(childAnim)
            }
            listener?.onAnimationCreateFinish(animationSet)
        }
        return animationSet
    }
}