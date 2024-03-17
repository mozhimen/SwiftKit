package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.View
import android.view.Window

/**
 * @ClassName UtilKPeekDecorView
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/16 23:04
 * @Version 1.0
 */
object UtilKPeekDecorView {
    @JvmStatic
    fun get(window: Window): View? =
        UtilKWindow.getPeekDecorView(window)

    @JvmStatic
    fun get(activity: Activity): View? =
        get(activity.window)
}