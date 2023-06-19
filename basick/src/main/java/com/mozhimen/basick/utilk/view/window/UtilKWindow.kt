package com.mozhimen.basick.utilk.view.window

import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basick.utilk.view.bar.UtilKNavigationBar
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBar
import kotlin.math.abs

/**
 * @ClassName UtilKWindow
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/27 23:14
 * @Version 1.0
 */
object UtilKWindow {
    private const val TAG = "UtilKWindow>>>>>"

    /**
     * 获取window
     * @param activity Activity
     * @return Window
     */
    @JvmStatic
    fun get(activity: Activity): Window =
        activity.window

    /**
     * 获取DecorView
     * @param activity Activity
     * @return View
     */
    @JvmStatic
    fun getDecorView(activity: Activity) =
        get(activity).decorView

    /**
     * 获取PeekDecorView
     * @param activity Activity
     * @return View
     */
    @JvmStatic
    fun getPeekDecorView(activity: Activity) =
        get(activity).peekDecorView()

    /**
     * 获取DecorView
     * @param window Window
     * @return View
     */
    @JvmStatic
    fun getDecorView(window: Window) =
        window.decorView

    /**
     * 获取DecorView
     * @param window Window
     * @return View
     */
    @JvmStatic
    fun getPeekDecorView(window: Window) =
        window.peekDecorView()

    /**
     * 获取Attributes
     * @param activity Activity
     * @return (LayoutParams..LayoutParams?)
     */
    @JvmStatic
    fun getAttributes(activity: Activity) =
        get(activity).attributes

    /**
     * 获取Attributes
     * @param window Window
     * @return (LayoutParams..LayoutParams?)
     */
    @JvmStatic
    fun getAttributes(window: Window) =
        window.attributes

    /**
     * 是否全屏
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        if (activity.window == null) false else getAttributes(activity).flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN


    /**
     * 获取DecorView区域高度
     * @param window Window
     * @return Int
     */
    @JvmStatic
    fun getDecorViewInvisibleHeight(window: Window): Int {
        val decorView = getDecorView(window)
        val outRect = Rect()
        decorView.getWindowVisibleDisplayFrame(outRect)
        Log.d(TAG, "getDecorViewInvisibleHeight: " + (decorView.bottom - outRect.bottom))
        val delta = abs(decorView.bottom - outRect.bottom)
        return if (delta <= UtilKNavigationBar.getNavigationBarHeight() + UtilKStatusBar.getStatusBarHeight()) 0 else delta
    }

    /**
     * 获取其ContentView区域高度
     * @param window Window
     * @return Int
     */
    @JvmStatic
    fun getContentViewInvisibleHeight(window: Window): Int {
        val contentView = window.findViewById<View>(android.R.id.content) ?: return 0
        val outRect = Rect()
        contentView.getWindowVisibleDisplayFrame(outRect)
        Log.d(TAG, "getContentViewInvisibleHeight: " + (contentView.bottom - outRect.bottom))
        val delta = abs(contentView.bottom - outRect.bottom)
        return if (delta <= UtilKStatusBar.getStatusBarHeight() + UtilKNavigationBar.getNavigationBarHeight()) 0 else delta
    }
}