package com.mozhimen.basick.utilk.view.window

import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mozhimen.basick.elemk.cons.CView
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
        get(activity.window)

    @JvmStatic
    fun get(window: Window): View =
        UtilKWindow.getDecorView(window)

    @JvmStatic
    fun getViewGroup(activity: Activity): ViewGroup =
        getViewGroup(activity.window)

    @JvmStatic
    fun getViewGroup(window: Window): ViewGroup =
        get(window) as ViewGroup

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun setSystemUiVisibility(activity: Activity, visibility: Int) {
        setSystemUiVisibility(activity.window, visibility)
    }

    @JvmStatic
    fun setSystemUiVisibility(window: Window, visibility: Int) {
        setSystemUiVisibility(get(window), visibility)
    }

    @JvmStatic
    fun setSystemUiVisibility(decorView: View, visibility: Int) {
        decorView.systemUiVisibility = visibility
    }

    @JvmStatic
    fun getSystemUiVisibility(activity: Activity): Int =
        getSystemUiVisibility(activity.window)

    @JvmStatic
    fun getSystemUiVisibility(window: Window): Int =
        get(window).systemUiVisibility

    @JvmStatic
    fun getWindowSystemUiVisibility(activity: Activity): Int =
        getWindowSystemUiVisibility(activity.window)

    @JvmStatic
    fun getWindowSystemUiVisibility(window: Window): Int =
        get(window).windowSystemUiVisibility

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getWindowVisibleDisplayFrame(activity: Activity, rect: Rect) {
        getWindowVisibleDisplayFrame(get(activity), rect)
    }

    @JvmStatic
    fun getWindowVisibleDisplayFrame(view: View, rect: Rect) {
        UtilKView.getWindowVisibleDisplayFrame(view, rect)
    }

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getInvisibleHeight(activity: Activity): Int =
        getInvisibleHeight(UtilKWindow.get(activity))

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

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun setFullScreen(activity: Activity) {
        setFullScreen(activity.window)
    }

    @JvmStatic
    fun setFullScreen(window: Window) {
        setFullScreen(get(window))
    }

    /**
     * 设置全屏
     * @param decorView View
     */
    @JvmStatic
    fun setFullScreen(decorView: View) {
        setSystemUiVisibility(
            decorView, (CView.FLAG_LOW_PROFILE or
                    CView.FLAG_FULLSCREEN or
                    CView.FLAG_LAYOUT_STABLE or
                    CView.FLAG_IMMERSIVE_STICKY or
                    CView.FLAG_LAYOUT_HIDE_NAVIGATION or
                    CView.FLAG_HIDE_NAVIGATION)
        )
    }
}