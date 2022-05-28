package com.mozhimen.basick.utilk

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.*

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

    fun startFlash(
        view: View,
        startAlpha: Float = 1f,
        endAlpha: Float = 0f,
        duration: Long = 1000,
        repeatCount: Int = ValueAnimator.INFINITE,
        repeatMode: Int = ValueAnimator.RESTART,
    ) {
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha, startAlpha)
        objectAnimator.repeatCount = repeatCount
        objectAnimator.repeatMode = repeatMode
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    fun startShake(
        view: View,
        isShakeY: Boolean = true,
        shakeDistance: Float = view.width.coerceAtLeast(view.height) / 2f,
        duration: Long = 1000,
        repeatCount: Int = Animation.INFINITE,
        repeatMode: Int = Animation.RESTART,
        listener: Animation.AnimationListener? = null
    ) {
        val transAnimation = if (isShakeY) TranslateAnimation(0f, 0f, 0f, shakeDistance) else TranslateAnimation(0f, shakeDistance, 0f, 0f)
        transAnimation.interpolator = CycleInterpolator(1f)
        transAnimation.repeatCount = repeatCount
        transAnimation.repeatMode = repeatMode
        transAnimation.fillAfter = true
        transAnimation.duration = duration
        listener?.let {
            transAnimation.setAnimationListener(it)
        }
        view.startAnimation(transAnimation)
    }

    fun stopAnim(view: View) {
        view.apply {
            animation?.cancel()
            view.clearAnimation()
        }
    }
}