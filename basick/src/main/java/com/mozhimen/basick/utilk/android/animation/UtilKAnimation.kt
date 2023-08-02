package com.mozhimen.basick.utilk.android.animation

import android.view.animation.Animation

object UtilKAnimation {
    /**
     * 获取时长
     * @param animation Animation
     * @return Long
     */
    @JvmStatic
    fun getAnimationDuration(animation: Animation): Long {
        return if (animation.duration <= 0) 0 else animation.duration
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 释放Animation
     * @param animation Animation
     */
    @JvmStatic
    fun releaseAnimation(animation: Animation) {
        animation.cancel()
        animation.setAnimationListener(null)
    }
}