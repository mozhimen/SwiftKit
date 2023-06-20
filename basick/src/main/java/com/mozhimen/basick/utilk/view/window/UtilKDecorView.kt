package com.mozhimen.basick.utilk.view.window

import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.view.UtilKView
import com.mozhimen.basick.utilk.view.bar.UtilKNavigationBar
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBar
import kotlin.math.abs

/**
 * @ClassName UtilKDecorView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 15:45
 * @Version 1.0
 */
object UtilKDecorView : BaseUtilK() {
    @JvmStatic
    fun get(activity: Activity): View =
        UtilKWindow.getDecorView(activity)

    @JvmStatic
    fun get(window: Window): View =
        UtilKWindow.getDecorView(window)

    @JvmStatic
    fun getViewGroup(activity: Activity): ViewGroup =
        get(activity) as ViewGroup

    @JvmStatic
    fun getViewGroup(window: Window): ViewGroup =
        get(window) as ViewGroup

    @JvmStatic
    fun setSystemUiVisibility(activity: Activity, visibility: Int) {
        get(activity).systemUiVisibility = visibility
    }

    @JvmStatic
    fun setSystemUiVisibility(window: Window, visibility: Int) {
        get(window).systemUiVisibility = visibility
    }

    @JvmStatic
    fun getSystemUiVisibility(activity: Activity): Int =
        get(activity).systemUiVisibility

    @JvmStatic
    fun getSystemUiVisibility(window: Window): Int =
        get(window).systemUiVisibility

    @JvmStatic
    fun getWindowSystemUiVisibility(activity: Activity): Int =
        get(activity).windowSystemUiVisibility

    @JvmStatic
    fun getWindowSystemUiVisibility(window: Window): Int =
        get(window).windowSystemUiVisibility

    @JvmStatic
    fun getInvisibleHeight(activity: Activity): Int =
        getInvisibleHeight(UtilKWindow.get(activity))

    @JvmStatic
    fun getWindowVisibleDisplayFrame(activity: Activity, rect: Rect) {
        UtilKView.getWindowVisibleDisplayFrame(get(activity), rect)
    }

    @JvmStatic
    fun getWindowVisibleDisplayFrame(view: View, rect: Rect) {
        UtilKView.getWindowVisibleDisplayFrame(view, rect)
    }

    /**
     * 获取DecorView区域高度
     * @param window Window
     * @return Int
     */
    @JvmStatic
    fun getInvisibleHeight(window: Window): Int {
        val decorView = UtilKWindow.getDecorView(window)
        val outRect = Rect()
        getWindowVisibleDisplayFrame(decorView, outRect)
        Log.d(TAG, "getInvisibleHeight: " + (decorView.bottom - outRect.bottom))
        val delta = abs(decorView.bottom - outRect.bottom)
        return if (delta <= UtilKNavigationBar.getNavigationBarHeight() + UtilKStatusBar.getStatusBarHeight()) 0 else delta
    }
}