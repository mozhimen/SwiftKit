package com.mozhimen.basick.utilk.android.animation

import android.animation.Animator
import android.animation.AnimatorSet
import kotlin.math.max

/**
 * @ClassName UtilKAnimator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/22 23:06
 * @Version 1.0
 */
object UtilKAnimator {
    /**
     * 获取时长
     * @param animator Animator
     * @return Long
     */
    @JvmStatic
    fun getDuration(animator: Animator): Long {
        var duration: Long
        if (animator is AnimatorSet) {
            duration = animator.duration
            if (duration <= 0) {
                for (childAnimation in animator.childAnimations)
                    duration = max(duration, childAnimation.duration)
            }
        } else duration = animator.duration
        return if (duration <= 0) 0 else duration
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 释放Animator
     * @param animator Animator
     */
    @JvmStatic
    fun cancel_removeAllListeners(animator: Animator) {
        animator.cancel()
        animator.removeAllListeners()
    }
}