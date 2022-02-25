package com.mozhimen.basicsmk.utilmk

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * @ClassName UtilMKStatusBar
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/2 18:26
 * @Version 1.0
 */
object UtilMKStatusBar {
    /**
     * lightTheme true: 白底黑字, false: 黑底白字
     * statusBarColor 状态栏的背景色
     * translucent 沉浸式的效果, 页面的布局延伸到状态栏之下
     */
    fun setStatusBar(
        activity: Activity,
        lightTheme: Boolean = true,
        statusBarColor: Int = Color.WHITE,
        translucent: Boolean = false
    ) {
        val window = activity.window
        val decorView = window.decorView
        var visibility = decorView.systemUiVisibility

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //请求系统 绘制状态栏的背景色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //这俩不能同时出现
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = statusBarColor
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            visibility = if (lightTheme) {
                //白底黑字-浅色主题
                visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                //黑底白字-深色主题
                visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }

        if (translucent) {
            //此时 能够使得页面的布局延申到状态栏之下,但是状态栏的图标也不见了,使得状态栏的图标恢复可见性
            visibility = visibility or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        decorView.systemUiVisibility = visibility
    }
}