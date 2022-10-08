package com.mozhimen.componentk.animk.helpers

import android.view.View
import android.view.animation.*
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.componentk.animk.mos.AnimationMo

object AnimationHelper {
    private fun <T> genAnimation(animationMo: AnimationMo, animation: T): T where T : Animation {
        animation.fillAfter = animationMo.fillAfter
        animation.repeatCount = animationMo.repeatCount
        animation.repeatMode = animationMo.repeatMode
        animation.interpolator = animationMo.interpolator
        animation.duration = animationMo.duration
        animationMo.listener?.let { animation.setAnimationListener(it) }
        return animation
    }

    /**
     * 开始旋转
     * @param animationMo AnimationMo
     * @param pivotX Float
     * @param pivotY Float
     * @return Animation
     */
    @JvmStatic
    fun rotate(
        animationMo: AnimationMo,
        pivotX: Float = animationMo.view.width / 2f,
        pivotY: Float = animationMo.view.height / 2f,
    ): Animation =
        genAnimation(animationMo, RotateAnimation(0f, 360f, pivotX, pivotY))

    /**
     * 开始抖动:interpolator: Interpolator = AccelerateDecelerateInterpolator
     * @param animationMo AnimationMo
     * @param isShakeY Boolean
     * @param shakeDistance Float
     * @return Animation
     */
    @JvmStatic
    fun shake(
        animationMo: AnimationMo,
        isShakeY: Boolean = true,
        shakeDistance: Float = animationMo.view.width.coerceAtLeast(animationMo.view.height) / 2f,
    ): Animation =
        genAnimation(animationMo, if (isShakeY) TranslateAnimation(0f, 0f, 0f, shakeDistance) else TranslateAnimation(0f, shakeDistance, 0f, 0f))


    /**
     * 从右边进入:repeatCount: Int = 1,interpolator: Interpolator = AccelerateInterpolator(),
     * @param animationMo AnimationMo
     * @return Animation
     */
    @JvmStatic
    fun transInRight(
        animationMo: AnimationMo
    ): Animation =
        genAnimation(animationMo, TranslateAnimation(UtilKScreen.getScreenWidth().toFloat(), 0f, 0f, 0f))

    /**
     * 从右边退出:repeatCount: Int = 1,interpolator: Interpolator = AccelerateInterpolator(),
     * @param animationMo AnimationMo
     * @return Animation
     */
    @JvmStatic
    fun transOutRight(
        animationMo: AnimationMo
    ): Animation =
        genAnimation(animationMo, TranslateAnimation(0f, UtilKScreen.getScreenWidth().toFloat(), 0f, 0f))
}