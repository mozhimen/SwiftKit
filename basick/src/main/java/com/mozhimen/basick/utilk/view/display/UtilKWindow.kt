package com.mozhimen.basick.utilk.view.display

import android.R
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.Window
import com.mozhimen.basick.utilk.view.bar.UtilKNavigationBar
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBar
import com.mozhimen.basick.utilk.view.keyboard.UtilKKeyBoard
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
    private var _decorViewDelta = 0

    /**
     * 获取DecorView区域高度
     * @param window Window
     * @return Int
     */
    @JvmStatic
    fun getDecorViewInvisibleHeight(window: Window): Int {
        val decorView = window.decorView
        val outRect = Rect()
        decorView.getWindowVisibleDisplayFrame(outRect)
        Log.d(TAG, "getDecorViewInvisibleHeight: " + (decorView.bottom - outRect.bottom))
        val delta = abs(decorView.bottom - outRect.bottom)
        if (delta <= UtilKNavigationBar.getNavigationBarHeight() + UtilKStatusBar.getStatusBarHeight()) {
            _decorViewDelta = delta
            return 0
        }
        return delta - _decorViewDelta
    }

    /**
     * 获取其ContentView区域高度
     * @param window Window
     * @return Int
     */
    @JvmStatic
    fun getContentViewInvisibleHeight(window: Window): Int {
        val contentView = window.findViewById<View>(R.id.content) ?: return 0
        val outRect = Rect()
        contentView.getWindowVisibleDisplayFrame(outRect)
        Log.d(TAG, "getContentViewInvisibleHeight: " + (contentView.bottom - outRect.bottom))
        val delta = abs(contentView.bottom - outRect.bottom)
        return if (delta <= UtilKStatusBar.getStatusBarHeight() + UtilKNavigationBar.getNavigationBarHeight()) 0 else delta
    }
}