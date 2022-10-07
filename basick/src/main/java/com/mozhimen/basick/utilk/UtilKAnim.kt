package com.mozhimen.basick.utilk

import android.animation.*
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.animation.*
import androidx.core.view.ViewCompat

/**
 * @ClassName UtilKAnim
 * @Description 不推荐直接使用,因为属性动画需要不及时释放,会造成内存泄露
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/18 0:25
 * @Version 1.0
 */
object UtilKAnim {
    private const val TAG = "UtilKAnim>>>>>"

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
    ) {
        val rotateAnim = RotateAnimation(0f, 360f, pivotX, pivotY)
        rotateAnim.fillAfter = fillAfter
        rotateAnim.repeatCount = repeatCount
        rotateAnim.repeatMode = repeatMode
        rotateAnim.interpolator = interpolator
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
     * @param repeatCount Int
     * @param repeatMode Int
     * @param interpolator Interpolator
     * @param duration Long
     * @param listener AnimatorListener?
     */
    @JvmStatic
    fun flash(
        view: View,
        startAlpha: Float = 1f,
        endAlpha: Float = 0f,
        repeatCount: Int = ValueAnimator.INFINITE,
        repeatMode: Int = ValueAnimator.RESTART,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        duration: Long = 1000,
        listener: Animator.AnimatorListener? = null
    ) {
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha, startAlpha)
        objectAnimator.interpolator = interpolator
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
     * @param fillAfter Boolean
     * @param repeatCount Int
     * @param repeatMode Int
     * @param interpolator Interpolator
     * @param duration Long
     * @param listener AnimationListener?
     */
    @JvmStatic
    fun shake(
        view: View,
        isShakeY: Boolean = true,
        shakeDistance: Float = view.width.coerceAtLeast(view.height) / 2f,
        fillAfter: Boolean = true,
        repeatCount: Int = Animation.INFINITE,
        repeatMode: Int = Animation.RESTART,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ) {
        val transAnimation = if (isShakeY) TranslateAnimation(0f, 0f, 0f, shakeDistance) else TranslateAnimation(0f, shakeDistance, 0f, 0f)
        transAnimation.fillAfter = fillAfter
        transAnimation.interpolator = interpolator
        transAnimation.repeatCount = repeatCount
        transAnimation.repeatMode = repeatMode
        transAnimation.duration = duration
        listener?.let {
            transAnimation.setAnimationListener(it)
        }
        view.startAnimation(transAnimation)
    }

    /**
     * 从右边进入
     * @param view View
     * @param fillAfter Boolean
     * @param interpolator Interpolator
     * @param duration Long
     * @param listener AnimationListener?
     */
    @JvmStatic
    fun transInRight(
        view: View,
        fillAfter: Boolean = true,
        interpolator: Interpolator = AccelerateInterpolator(),
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ) {
        val transAnimation = TranslateAnimation(UtilKScreen.getScreenWidth().toFloat(), 0f, 0f, 0f)
        transAnimation.fillAfter = fillAfter
        transAnimation.interpolator = interpolator
        transAnimation.duration = duration
        listener?.let {
            transAnimation.setAnimationListener(it)
        }
        view.startAnimation(transAnimation)
    }

    /**
     * 从右边退出
     * @param view View
     * @param fillAfter Boolean
     * @param interpolator Interpolator
     * @param duration Long
     * @param listener AnimationListener?
     */
    @JvmStatic
    fun transOutRight(
        view: View,
        fillAfter: Boolean = true,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        duration: Long = 1000,
        listener: Animation.AnimationListener? = null
    ) {
        val transAnimation = TranslateAnimation(0f, UtilKScreen.getScreenWidth().toFloat(), 0f, 0f)
        transAnimation.fillAfter = fillAfter
        transAnimation.interpolator = interpolator
        transAnimation.duration = duration
        listener?.let {
            transAnimation.setAnimationListener(it)
        }
        view.startAnimation(transAnimation)
    }

    /**
     * 开始扩大然后缩小.像波浪一样
     * @param view View
     * @param ratio Float
     * @param repeatCount Int
     * @param repeatMode Int
     * @param interpolator Interpolator
     * @param duration Long
     * @param listener AnimatorListener?
     */
    @JvmStatic
    fun waver(
        view: View,
        ratio: Float = 1.1f,
        repeatCount: Int = ValueAnimator.INFINITE,
        repeatMode: Int = ValueAnimator.RESTART,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        duration: Long = 1000,
        listener: Animator.AnimatorListener? = null
    ) {
        view.pivotX = view.width / 2f
        view.pivotY = view.height / 2f
        val objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, ratio, 1.0f)
        val objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, ratio, 1.0f)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, objectAnimatorScaleX, objectAnimatorScaleY)
        objectAnimator.interpolator = interpolator
        objectAnimator.repeatCount = repeatCount
        objectAnimator.repeatMode = repeatMode
        objectAnimator.duration = duration
        listener?.let {
            objectAnimator.addListener(it)
        }
        objectAnimator.start()
    }

    /**
     * 背景变换
     * @param view View
     * @param colorStart Int
     * @param colorEnd Int
     * @param repeatCount Int
     * @param repeatMode Int
     * @param interpolator Interpolator
     * @param duration Long
     * @param listener AnimatorListener?
     */
    @JvmStatic
    fun bgBetweenColors(
        view: View,
        colorStart: Int,
        colorEnd: Int,
        repeatCount: Int = ValueAnimator.INFINITE,
        repeatMode: Int = ValueAnimator.RESTART,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        duration: Long = 1000,
        listener: Animator.AnimatorListener? = null
    ) {
        val colorDrawable = ColorDrawable(colorStart)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorStart, colorEnd, colorStart)
        colorAnimation.addUpdateListener { animator: ValueAnimator ->
            colorDrawable.color = (animator.animatedValue as Int)
            ViewCompat.setBackground(view, colorDrawable)
        }
        colorAnimation.repeatCount = repeatCount
        colorAnimation.repeatMode = repeatMode
        colorAnimation.interpolator = interpolator
        colorAnimation.duration = duration
        listener?.let {
            colorAnimation.addListener(it)
        }
        colorAnimation.start()
    }

    @JvmStatic
    fun bgAlphaFlash(
        view: View,
        alphaEnd: Int = 0,
        repeatCount: Int = ValueAnimator.INFINITE,
        repeatMode: Int = ValueAnimator.RESTART,
        interpolator: Interpolator = AccelerateDecelerateInterpolator(),
        duration: Long = 1000,
        listener: Animator.AnimatorListener? = null
    ) {
        val alphaDrawable = view.background
        val alphaAnimation = ValueAnimator.ofInt(255, alphaEnd, 255)
        alphaAnimation.addUpdateListener {
            alphaDrawable.alpha = it.animatedValue as Int
            ViewCompat.setBackground(view, alphaDrawable)
        }
        alphaAnimation.repeatCount = repeatCount
        alphaAnimation.repeatMode = repeatMode
        alphaAnimation.interpolator = interpolator
        alphaAnimation.duration = duration
        listener?.let {
            alphaAnimation.addListener(it)
        }
        alphaAnimation.start()
    }

    @JvmStatic
    fun stopAnim(view: View) {
        view.apply {
            animation?.cancel()
            view.clearAnimation()
        }
    }
}