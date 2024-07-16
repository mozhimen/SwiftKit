package com.mozhimen.basick.utilk.android.view

import android.view.animation.Animation


fun Animation.cancel_releaseAnimationListener() {
    UtilKAnimation.cancel_releaseAnimationListener(this)
}

//////////////////////////////////////////////////////////////////////////////////////////

object UtilKAnimation {
    /**
     * 获取时长
     * @param animation Animation
     * @return Long
     */
    @JvmStatic
    fun getDuration(animation: Animation): Long =
        if (animation.duration <= 0) 0
        else animation.duration

    //////////////////////////////////////////////////////////////////////////////////////////

    //释放Animation
    @JvmStatic
    fun cancel_releaseAnimationListener(animation: Animation) {
        animation.cancel()
        animation.setAnimationListener(null)
    }
}