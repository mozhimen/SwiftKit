package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mozhimen.basick.elemk.android.view.cons.CView
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK
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
    fun getContentView(activity: Activity): View? =
        get(activity).findViewById(CPackage.ANDROID_R_ID_CONTENT)

    @JvmStatic
    fun getAsViewGroup(activity: Activity): ViewGroup =
        getAsViewGroup(activity.window)

    @JvmStatic
    fun getAsViewGroup(window: Window): ViewGroup =
        getAs(window)

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <V : View> getAs(window: Window): V =
        get(window) as V

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getSystemUiVisibility(activity: Activity): Int =
        getSystemUiVisibility(activity.window)

    @JvmStatic
    fun getSystemUiVisibility(window: Window): Int =
        getSystemUiVisibility(get(window))

    @JvmStatic
    fun getSystemUiVisibility(decorView: View): Int =
        decorView.systemUiVisibility

    @JvmStatic
    fun getWindowSystemUiVisibility(activity: Activity): Int =
        getWindowSystemUiVisibility(activity.window)

    @JvmStatic
    fun getWindowSystemUiVisibility(window: Window): Int =
        get(window).windowSystemUiVisibility

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
        val rect = Rect()
        getWindowVisibleDisplayFrame(decorView, rect)
        val delta = abs(decorView.bottom - rect.bottom)
        return (if (delta <= UtilKNavigationBar.getHeight() + UtilKStatusBar.getHeight()) 0 else delta).also { ("getInvisibleHeight: " + (decorView.bottom - rect.bottom)).dt(TAG) }
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 截屏
     */
    @JvmStatic
    fun getBitmap(activity: Activity): Bitmap {
        val decorView = get(activity)
        decorView.isDrawingCacheEnabled = true
        decorView.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(decorView.drawingCache, 0, 0, decorView.measuredWidth, decorView.measuredHeight - UtilKVirtualBar.getHeight(activity))
        decorView.isDrawingCacheEnabled = false
        decorView.destroyDrawingCache()
        return bitmap
    }

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applySystemUiVisibility(activity: Activity, visibility: Int) {
        applySystemUiVisibility(activity.window, visibility)
    }

    @JvmStatic
    fun applySystemUiVisibility(window: Window, visibility: Int) {
        applySystemUiVisibility(get(window), visibility)
    }

    @JvmStatic
    fun applySystemUiVisibility(decorView: View, visibility: Int) {
        decorView.systemUiVisibility = visibility
    }

    @JvmStatic
    fun applySystemUiVisibilityOr(activity: Activity, visibility: Int) {
        applySystemUiVisibilityOr(activity.window, visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityOr(window: Window, visibility: Int) {
        applySystemUiVisibilityOr(get(window), visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityOr(decorView: View, visibility: Int) {
        applySystemUiVisibility(decorView, getSystemUiVisibility(decorView) or visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityAnd(activity: Activity, visibility: Int) {
        applySystemUiVisibilityAnd(activity.window, visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityAnd(window: Window, visibility: Int) {
        applySystemUiVisibilityAnd(get(window), visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityAnd(decorView: View, visibility: Int) {
        applySystemUiVisibility(decorView, getSystemUiVisibility(decorView) and visibility)
    }

    @JvmStatic
    fun applyLayoutStable(activity: Activity) {
        applySystemUiVisibilityOr(activity, CView.SystemUiFlag.LAYOUT_STABLE)
    }

    @JvmStatic
    fun applyImmersedHard(activity: Activity) {
        applySystemUiVisibilityOr(activity, CView.SystemUiFlag.IMMERSIVE)
    }

    @JvmStatic
    fun applyImmersedSticky(activity: Activity) {
        applySystemUiVisibilityOr(activity, CView.SystemUiFlag.IMMERSIVE_STICKY)
    }

//    @JvmStatic
//    fun applyFullScreen(activity: Activity) {
//        setFullScreen(activity.window)
//    }
//
//    @JvmStatic
//    fun applyFullScreen(window: Window) {
//        setFullScreen(get(window))
//    }
//
//    /**
//     * 设置全屏
//     * @param decorView View
//     */
//    @JvmStatic
//    fun applyFullScreen(decorView: View) {
//        setSystemUiVisibility(
//            decorView, (CView.SystemUiFlag.LOW_PROFILE or
//                    CView.SystemUiFlag.FULLSCREEN or
//                    CView.SystemUiFlag.LAYOUT_STABLE or
//                    CView.SystemUiFlag.IMMERSIVE_STICKY or
//                    CView.SystemUiFlag.LAYOUT_HIDE_NAVIGATION or
//                    CView.SystemUiFlag.HIDE_NAVIGATION)
//        )
//    }
}