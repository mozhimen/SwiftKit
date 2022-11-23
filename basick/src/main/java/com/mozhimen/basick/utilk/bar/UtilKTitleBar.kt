package com.mozhimen.basick.utilk.bar

import android.app.Activity
import android.view.Window
import com.mozhimen.basick.utilk.view.UtilKView

/**
 * @ClassName UtilKTitleBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:34
 * @Version 1.0
 */
object UtilKTitleBar {
    /**
     * 获取标题栏高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getTitleBarHeight(activity: Activity) =
        UtilKView.getViewDrawHeight(activity) - UtilKStatusBar.getStatusBarHeight(activity)

    /**
     * 隐藏标题栏
     * @param activity Activity
     */
    @JvmStatic
    fun hideTitleBar(activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}