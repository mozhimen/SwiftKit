package com.mozhimen.basick.extsk

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.utilk.UtilKAnim

/**
 * @ClassName ExtsKAnim
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/18 9:06
 * @Version 1.0
 */
/**
 * 开始旋转
 * @receiver View
 * @param pivotX Float
 * @param pivotY Float
 * @param duration Long
 * @param repeatCount Int
 * @param repeatMode Int
 * @param listener AnimationListener?
 */
fun View.startRotate(
    pivotX: Float = width / 2f,
    pivotY: Float = height / 2f,
    fillAfter: Boolean = true,
    repeatCount: Int = Animation.INFINITE,
    repeatMode: Int = Animation.RESTART,
    interpolator: Interpolator = LinearInterpolator(),
    duration: Long = 1000,
    listener: Animation.AnimationListener? = null
) {
    UtilKAnim.rotate(
        this, pivotX, pivotY, fillAfter, repeatCount, repeatMode, interpolator, duration, listener
    )
}

/**
 * 结束动画
 * @receiver View
 */
fun View.stopAnim() {
    UtilKAnim.stopAnim(this)
}