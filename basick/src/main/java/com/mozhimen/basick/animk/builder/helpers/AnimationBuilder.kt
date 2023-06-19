package com.mozhimen.basick.animk.builder.helpers

import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.core.util.forEach
import com.mozhimen.basick.animk.builder.bases.BaseAnimationBuilder
import com.mozhimen.basick.animk.builder.commons.IAnimationCreateListener

/**
 * @ClassName AnimationBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:38
 * @Version 1.0
 */
class AnimationBuilder : BaseAnimationBuilder<AnimationBuilder>() {

    @JvmOverloads
    fun build(listener: IAnimationCreateListener? = null): Animation {
        val animationSet = AnimationSet(false)
        _types.forEach { _, value ->
            val childAnim = value.buildAnimation(_animKConfig)
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
        return animationSet
    }
}