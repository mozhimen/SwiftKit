package com.mozhimen.basick.utilk

import android.view.animation.Animation

object UtilKAnimation {
    @JvmStatic
    fun releaseAnimation(animation: Animation) {
        animation.cancel()
        animation.setAnimationListener(null)
    }

    /**
     * 获取时长
     * @param animation Animation
     * @return Long
     */
    @JvmStatic
    fun getAnimationDuration(animation: Animation): Long {
        return if (animation.duration < 0) 0 else animation.duration
    }
}