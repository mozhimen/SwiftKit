package com.mozhimen.basick.utilk

import android.view.View

object UtilKAnimation {
    @JvmStatic
    fun clearAnimation(view: View) {
        view.apply {
            animation?.cancel()
            view.clearAnimation()
        }
    }
}