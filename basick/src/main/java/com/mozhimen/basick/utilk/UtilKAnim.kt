package com.mozhimen.basick.utilk

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
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
    open class UtilKAnimationListener : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
    }

    open class UtilKAnimatorListener : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {}
        override fun onAnimationEnd(animator: Animator) {}
        override fun onAnimationCancel(animator: Animator) {}
        override fun onAnimationRepeat(animator: Animator) {}
    }

    /**
     * 开始旋转
     * @param view View
     * @param pivotX Float
     * @param pivotY Float
     * @param repeatCount Int
     * @param repeatMode Int
     * @param fillAfter Boolean
     * @param duration Long
     * @param listener AnimationListener?
     */
    fun startRotate(
        view: View,
        pivotX: Float = view.width / 2f,
        pivotY: Float = view.height / 2f,
        repeatCount: Int = Animation.INFINITE,
        repeatMode: Int = Animation.RESTART,
        fillAfter: Boolean = true,
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ) {
        val rotateAnim = RotateAnimation(0f, 360f, pivotX, pivotY)
        rotateAnim.interpolator = LinearInterpolator()
        rotateAnim.repeatCount = repeatCount
        rotateAnim.repeatMode = repeatMode
        rotateAnim.fillAfter = fillAfter
        rotateAnim.duration = duration
        listener?.let {
            rotateAnim.setAnimationListener(it)
        }
        view.startAnimation(rotateAnim)
    }

    /**
     * 开始闪动
     * @param view View
     * @param startAlpha Float
     * @param endAlpha Float
     * @param duration Long
     * @param repeatCount Int
     * @param repeatMode Int
     */
    fun startFlash(
        view: View,
        startAlpha: Float = 1f,
        endAlpha: Float = 0f,
        repeatCount: Int = ValueAnimator.INFINITE,
        repeatMode: Int = ValueAnimator.RESTART,
        duration: Long = 1000,
        listener: Animator.AnimatorListener? = null
    ) {
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha, startAlpha)
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.repeatCount = repeatCount
        objectAnimator.repeatMode = repeatMode
        objectAnimator.duration = duration
        listener?.let {
            objectAnimator.addListener(it)
        }
        objectAnimator.start()
    }

    /**
     * 开始抖动
     * @param view View
     * @param isShakeY Boolean
     * @param shakeDistance Float
     * @param repeatCount Int
     * @param repeatMode Int
     * @param fillAfter Boolean
     * @param duration Long
     * @param listener AnimationListener?
     */
    fun startShake(
        view: View,
        isShakeY: Boolean = true,
        shakeDistance: Float = view.width.coerceAtLeast(view.height) / 2f,
        repeatCount: Int = Animation.INFINITE,
        repeatMode: Int = Animation.RESTART,
        fillAfter: Boolean = true,
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ) {
        val transAnimation = if (isShakeY) TranslateAnimation(0f, 0f, 0f, shakeDistance) else TranslateAnimation(0f, shakeDistance, 0f, 0f)
        transAnimation.interpolator = CycleInterpolator(1f)
        transAnimation.repeatCount = repeatCount
        transAnimation.repeatMode = repeatMode
        transAnimation.fillAfter = fillAfter
        transAnimation.duration = duration
        listener?.let {
            transAnimation.setAnimationListener(it)
        }
        view.startAnimation(transAnimation)
    }

    /**
     * 从右边进入
     * @param view View
     * @param duration Long
     * @param fillAfter Boolean
     * @param listener AnimationListener?
     */
    fun transInRight(
        view: View,
        fillAfter: Boolean = true,
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ) {
        val transAnimation = TranslateAnimation(UtilKScreen.getScreenWidth().toFloat(), 0f, 0f, 0f)
        transAnimation.fillAfter = fillAfter
        transAnimation.duration = duration
        listener?.let {
            transAnimation.setAnimationListener(it)
        }
        view.startAnimation(transAnimation)
    }

    /**
     * 从右边退出
     * @param view View
     * @param duration Long
     * @param fillAfter Boolean
     * @param listener AnimationListener?
     */
    fun transOutRight(
        view: View,
        fillAfter: Boolean = true,
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ) {
        val transAnimation = TranslateAnimation(0f, UtilKScreen.getScreenWidth().toFloat(), 0f, 0f)
        transAnimation.fillAfter = fillAfter
        transAnimation.duration = duration
        listener?.let {
            transAnimation.setAnimationListener(it)
        }
        view.startAnimation(transAnimation)
    }

    /**
     * 开始扩大然后缩小
     * @param view View
     */
    fun startWaver(
        view: View,
        ratio: Float = 1.1f,
        repeatCount: Int = ValueAnimator.INFINITE,
        repeatMode: Int = ValueAnimator.RESTART,
        duration: Long = 1000,
        listener: Animator.AnimatorListener? = null
    ) {
        view.pivotX = view.width / 2f
        view.pivotY = view.height / 2f
        val objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, ratio, 1.0f)
        val objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, ratio, 1.0f)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, objectAnimatorScaleX, objectAnimatorScaleY)
        objectAnimator.duration = duration
        objectAnimator.repeatCount = repeatCount
        objectAnimator.repeatMode = repeatMode
        listener?.let {
            objectAnimator.addListener(it)
        }
        objectAnimator.start()
    }

    fun stopAnim(view: View) {
        view.apply {
            animation?.cancel()
            view.clearAnimation()
        }
    }
}