package com.mozhimen.swiftmk.utils.os

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
<<<<<<< HEAD
=======
import kotlin.math.sqrt
>>>>>>> b9e6f72a089183e4e672c0e1fda33ff2a94e8327

/**
 * @ClassName ScreenUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/21 9:16
 * @Version 1.0
 */
object ScreenUtil {
    private const val tag = "ScreenUtil"

    /**
     * 获取状态栏高度1
     * 优点: 不需要在Activity的回调方法onWindowFocusChanged()执行后才能得到结果
     * 缺点: 不管你是否设置全屏模式,或是不是显示状态栏,高度是固定的;因为系统资源属性是固定的,真实的,不管你是否隐藏(隐藏或显示),他都在nali
     */
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = -1
        //获取status_bar_height资源的ID
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     * 获取状态栏高度2
     * 优点: 依赖于WMS,是在界面构建后根据View获取的,所以高度是动态的
     * 缺点: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     */
    fun getStatusBarHeight(activity: Activity): Int {
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }

    /**
     * 获取标题栏高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     */
    fun getTitleHeight(activity: Activity) =
        getViewDrawHeight(activity) - getStatusBarHeight(activity)

    /**
     * 获取View绘制区域TOP高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期结果
     */
    private fun getViewDrawHeight(activity: Activity) =
        activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).top

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        display.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            display.getRealMetrics(displayMetrics)
        } else {
            display.getMetrics(displayMetrics)
        }
        return displayMetrics.heightPixels
    }

    /**
     * 获取虚拟功能键的高度
     */
    fun getVirtualBarHeight(context: Context): Int {
        var virtualBarHeight = 0
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        try {
            @SuppressWarnings("rawtypes")
            val cls = Class.forName("android.view.Display")

            @SuppressWarnings("unchecked")
            val method = cls.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, displayMetrics)
            virtualBarHeight = displayMetrics.heightPixels - display.height
        } catch (e: Exception) {
            Log.w(tag, e.message.toString())
        }
        return virtualBarHeight
    }

    /**
     * 截屏
     */
    fun captureScreen(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            view.getDrawingCache(),
            0,
            0,
            view.measuredWidth,
            view.measuredHeight - getVirtualBarHeight(activity)
        )
        view.isDrawingCacheEnabled = false
        view.destroyDrawingCache()
        return bitmap
    }

    /**
     * 关闭软键盘
     */
    fun closeSoftInputKeyBoard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
<<<<<<< HEAD
=======

    /**
     * 获取屏幕尺寸
     */
    fun getScreenSize(activity: Activity): Float {
        val densityDpi = activity.resources.displayMetrics.densityDpi
        val scaledDensity = activity.resources.displayMetrics.scaledDensity
        val density = activity.resources.displayMetrics.density
        val xdpi = activity.resources.displayMetrics.xdpi
        val ydpi = activity.resources.displayMetrics.ydpi
        val width = activity.resources.displayMetrics.widthPixels
        val height = activity.resources.displayMetrics.heightPixels

        //计算屏幕的物理尺寸
        val widthPhy = (width / xdpi) * (width / xdpi)
        val heightPhy = (height / ydpi) * (height / ydpi)
        return sqrt((widthPhy + heightPhy).toDouble()).toFloat()
    }
>>>>>>> b9e6f72a089183e4e672c0e1fda33ff2a94e8327
}