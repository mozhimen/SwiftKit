package com.mozhimen.basick.utilk.android.view

import android.app.Activity

/**
 * @ClassName UtilKSystemBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/28 17:12
 * @Version 1.0
 */
object UtilKSystemBar {
    /**
     * 设置全屏
     * @param activity Activity
     */
    @JvmStatic
    fun setImmersed(activity: Activity) {
        UtilKTitleBar.hide(activity)
        UtilKStatusBar.hide(activity)
        UtilKContentView.setFitsSystemWindows(activity)
    }
}