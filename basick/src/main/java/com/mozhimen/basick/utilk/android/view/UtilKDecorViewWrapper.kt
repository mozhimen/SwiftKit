package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.Window
import com.mozhimen.basick.utilk.android.util.d
import kotlin.math.abs

/**
 * @ClassName UtilKDecorViewWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/22
 * @Version 1.0
 */
object UtilKDecorViewWrapper {
    @JvmStatic
    fun getInvisibleHeight(activity: Activity): Int =
        getInvisibleHeight(UtilKWindow.get(activity))

    //获取DecorView区域高度
    @JvmStatic
    fun getInvisibleHeight(window: Window): Int {
        val decorView = UtilKWindow.getDecorView(window)
        val outRect = UtilKDecorView.getWindowVisibleDisplayFrame(window)
        val delta = abs(decorView.bottom - outRect.bottom)
        return (if (delta <= UtilKNavigationBar.getHeight() + UtilKStatusBar.getHeight()) 0 else delta).also { ("getInvisibleHeight: " + (decorView.bottom - outRect.bottom)).d(UtilKDecorView.TAG) }
    }
}