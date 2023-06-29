package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import com.mozhimen.basick.utilk.androidx.appcompat.UtilKActionBar

/**
 * @ClassName UtilKSystemBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/28 17:12
 * @Version 1.0
 */
object UtilKSystemBar {
    @JvmStatic
    fun setStatusBarLowProfile(activity: Activity) {
        UtilKStatusBarIcon.setLowProfile(activity)
    }

    @JvmStatic
    fun setImmersed(activity: Activity) {
        UtilKTitleBar.hide(activity)
        UtilKActionBar.hide(activity)
        UtilKStatusBar.hide(activity)
        UtilKNavigationBar.hide(activity)
    }
}