package com.mozhimen.basick.animk.builder.commons

import android.view.animation.Animation
import android.view.animation.AnimationSet

/**
 * @ClassName OnAnimationCreateListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:30
 * @Version 1.0
 */
interface IAnimationCreateListener {
    fun onAnimationCreated(animation: Animation)
    fun onAnimationCreateFinish(animationSet: AnimationSet) {}
}