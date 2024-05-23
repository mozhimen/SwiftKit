package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.View
import android.view.Window
import com.mozhimen.basick.elemk.android.view.cons.CView
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
        val decorView = UtilKDecorView.get(window)
        val outRect = UtilKDecorView.getWindowVisibleDisplayFrame(window)
        val delta = abs(decorView.bottom - outRect.bottom)
        return (if (delta <= UtilKNavigationBar.getHeight() + UtilKStatusBar.getHeight()) 0 else delta).also { ("getInvisibleHeight: " + (decorView.bottom - outRect.bottom)).d(UtilKDecorView.TAG) }
    }

    @JvmStatic
    fun applyImmersive(activity: Activity) {
        applyImmersive(UtilKWindow.get(activity))
    }

    @JvmStatic
    fun applyImmersive(window: Window) {
        UtilKDecorView.applySystemUiVisibility(
            window,
            CView.SystemUiFlag.IMMERSIVE_STICKY
                    or CView.SystemUiFlag.LAYOUT_STABLE
                    or CView.SystemUiFlag.LAYOUT_HIDE_NAVIGATION
                    or CView.SystemUiFlag.LAYOUT_FULLSCREEN
                    or CView.SystemUiFlag.HIDE_NAVIGATION
                    or CView.SystemUiFlag.FULLSCREEN
        )
    }
}