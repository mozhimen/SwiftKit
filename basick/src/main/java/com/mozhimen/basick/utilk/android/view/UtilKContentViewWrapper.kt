package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.View
import android.view.Window
import kotlin.math.abs

/**
 * @ClassName UtilKContentViewWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/16 22:57
 * @Version 1.0
 */
object UtilKContentViewWrapper {

    @JvmStatic
    fun getInvisibleHeight_ofPac(activity: Activity): Int =
        getInvisibleHeight_ofPac(UtilKWindow.get(activity))

    @JvmStatic
    fun getInvisibleHeight_ofPac(window: Window): Int {
        val contentView = UtilKContentView.get_ofPac<View>(window)
        val outRect = UtilKContentView.getWindowVisibleDisplayFrame_ofPac(window)
        val delta = abs(contentView.bottom - outRect.bottom)
        return if (delta <= UtilKStatusBar.getHeight() + UtilKNavigationBar.getHeight()) 0
        else delta
    }

    /**
     * 获取View绘制区域TOP高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期结果
     */
    @JvmStatic
    fun getViewDrawHeight_ofWin(activity: Activity): Int =
        UtilKContentView.getTop_ofWin(activity)

    /**
     * 获取View绘制区域TOP高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期结果
     */
    @JvmStatic
    fun getViewDrawHeight_ofPac(activity: Activity): Int =
        UtilKContentView.getTop_ofPac(activity)
}