package com.mozhimen.basicsk.extsk

import android.view.View
import android.view.animation.Animation
import com.mozhimen.basicsk.utilk.UtilKAnim

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
    pivotX: Float = this.width / 2f,
    pivotY: Float = this.height / 2f,
    duration: Long = 1000,
    repeatCount: Int = Animation.INFINITE,
    repeatMode: Int = Animation.RESTART,
    listener: Animation.AnimationListener? = null
) {
    UtilKAnim.startRotate(this, pivotX, pivotY, duration, repeatCount, repeatMode, listener)
}

/**
 * 结束动画
 * @receiver View
 */
fun View.stopAnim() {
    UtilKAnim.stopAnim(this)
}