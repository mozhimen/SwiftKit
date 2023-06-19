package com.mozhimen.basick.animk.builder.commons

import android.animation.Animator
import android.animation.AnimatorSet

/**
 * @ClassName OnAnimatorCreateListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:30
 * @Version 1.0
 */
interface IAnimatorCreateListener {
    fun onAnimatorCreated(animator: Animator)
    fun onAnimatorCreateFinish(animatorSet: AnimatorSet) {}
}