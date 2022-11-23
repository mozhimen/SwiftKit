package com.mozhimen.basick.utilk

import android.app.Activity
import android.view.WindowManager

/**
 * @ClassName UtilKWindow
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:13
 * @Version 1.0
 */
object UtilKWindow {
    /**
     * 是否全屏
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isActivityFullScreen(activity: Activity): Boolean =
        if (activity.window == null) false else activity.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
}