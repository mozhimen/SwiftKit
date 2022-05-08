package com.mozhimen.basicsk.utilk

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basicsk.logk.LogK

/**
 * @ClassName ScreenUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/21 9:16
 * @Version 1.0
 */
object UtilKScreen {
    private val TAG = "UtilKScreen>>>>>"

    private val _context = UtilKGlobal.instance.getApp()!!

    /**
     * 获取状态栏高度1
     * 优点: 不需要在Activity的回调方法onWindowFocusChanged()执行后才能得到结果
     * 缺点: 不管你是否设置全屏模式,或是不是显示状态栏,高度是固定的;因为系统资源属性是固定的,真实的,不管你是否隐藏(隐藏或显示),他都在nali
     * @return Int
     */
    fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val typedArray = _context.theme.obtainStyledAttributes(
            intArrayOf(
                android.R.attr.windowFullscreen
            )
        )
        val windowFullscreen = typedArray.getBoolean(0, false)
        typedArray.recycle()
        if (windowFullscreen) {
            return statusBarHeight
        }
        //获取status_bar_height资源的ID
        val resourceId = _context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = _context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     * 获取状态栏高度2
     * 优点: 依赖于WMS,是在界面构建后根据View获取的,所以高度是动态的
     * 缺点: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     * @param activity Activity
     * @return Int
     */
    fun getStatusBarHeight(activity: Activity): Int {
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }

    /**
     * 获取标题栏高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     * @param activity Activity
     * @return Int
     */
    fun getTitleBarHeight(activity: Activity) =
        getViewDrawHeight(activity) - getStatusBarHeight(activity)

    /**
     * 获取View绘制区域TOP高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期结果
     * @param activity Activity
     * @return Int
     */
    private fun getViewDrawHeight(activity: Activity) =
        activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).top

    /**
     * 获取像素宽
     * @return Int
     */
    fun getScreenWidth(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            (_context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).currentWindowMetrics.bounds.width()
        } else {
            val size = Point()
            val display = (_context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            display.getSize(size)
            size.x
        }

    /**
     * 获取像素高
     * @return Int
     */
    fun getScreenHeight(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            (_context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).currentWindowMetrics.bounds.height()
        } else {
            val size = Point()
            val display = (_context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            display.getSize(size)
            size.y
        }

    /**
     * 获取虚拟功能键的高度
     * @param context Context
     * @return Int
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
            LogK.wt(TAG, e.message.toString())
        }
        return virtualBarHeight
    }

    /**
     * 截屏
     * @param activity Activity
     * @return Bitmap
     */
    fun captureScreen(activity: Activity): Bitmap {
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
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @SuppressLint("PrivateApi", "SoonBlockedPrivateApi", "DiscouragedPrivateApi")
    fun closeAndroidPDialog() {
        try {
            val cls = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = cls.getDeclaredConstructor(String.javaClass)
            declaredConstructor.isAccessible = true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            LogK.et(TAG, e.message.toString())
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            LogK.et(TAG, e.message.toString())
        }
    }

    /**
     * 是否为竖屏
     * @return Boolean
     */
    fun isPortrait(): Boolean {
        val screenResolution = getScreenResolution()
        return screenResolution.y > screenResolution.x
    }

    private fun getScreenResolution(): Point {
        val wm = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val screenResolution = Point()
        display.getSize(screenResolution)
        return screenResolution
    }
}