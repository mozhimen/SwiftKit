package com.mozhimen.basick.utilk

import android.view.View
import android.view.animation.Animation

object UtilKAnimation {
    @JvmStatic
    fun clearAnimation(view: View) {
        view.apply {
            animation?.cancel()
            view.clearAnimation()
        }
    }

    @JvmStatic
    fun getAnimationDuration(animation: Animation): Long {
        return if (animation.duration < 0) 0 else animation.duration
    }
}