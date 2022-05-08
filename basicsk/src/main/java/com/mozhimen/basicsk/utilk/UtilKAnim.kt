package com.mozhimen.basicsk.utilk

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

/**
 * @ClassName UtilKAnim
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/18 0:25
 * @Version 1.0
 */
object UtilKAnim {
    fun startRotate(
        view: View,
        pivotX: Float = view.width / 2f,
        pivotY: Float = view.height / 2f,
        duration: Long = 1000,
        repeatCount: Int = Animation.INFINITE,
        repeatMode: Int = Animation.RESTART,
        listener: Animation.AnimationListener? = null
    ) {
        val rotateAnim = RotateAnimation(0f, 360f, pivotX, pivotY)
        rotateAnim.interpolator = LinearInterpolator()
        rotateAnim.repeatCount = repeatCount
        rotateAnim.repeatMode = repeatMode
        rotateAnim.fillAfter = true
        rotateAnim.duration = duration
        listener?.let {
            rotateAnim.setAnimationListener(it)
        }
        view.startAnimation(rotateAnim)
    }

    fun stopAnim(view: View) {
        view.apply {
            animation?.cancel()
            view.clearAnimation()
        }
    }
}