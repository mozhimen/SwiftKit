package com.mozhimen.componentk.animk.helpers

import android.view.View
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

object AnimationHelper {
    /**
     * 开始旋转
     * @param view View
     * @param pivotX Float
     * @param pivotY Float
     * @param fillAfter Boolean
     * @param repeatCount Int
     * @param repeatMode Int
     * @param interpolator Interpolator
     * @param duration Long
     * @param listener AnimationListener?
     */
    @JvmStatic
    fun rotate(
        view: View,
        pivotX: Float = view.width / 2f,
        pivotY: Float = view.height / 2f,
        fillAfter: Boolean = true,
        repeatCount: Int = Animation.INFINITE,
        repeatMode: Int = Animation.RESTART,
        interpolator: Interpolator = LinearInterpolator(),
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ): Animation {
        val animation = RotateAnimation(0f, 360f, pivotX, pivotY)
        animation.fillAfter = fillAfter
        animation.repeatCount = repeatCount
        animation.repeatMode = repeatMode
        animation.interpolator = interpolator
        animation.duration = duration
        listener?.let { animation.setAnimationListener(it) }
        return animation
        //view.startAnimation(animation)
    }
}