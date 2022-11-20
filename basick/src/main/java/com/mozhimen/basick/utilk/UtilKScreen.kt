package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basick.utilk.context.UtilKApplication

import kotlin.math.sqrt

/**
 * @ClassName ScreenUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/21 9:16
 * @Version 1.0
 */
object UtilKScreen {
    private val TAG = "UtilKScreen>>>>>"

    private val _context = UtilKApplication.instance.get()!!

    /**
     * 获得View所在界面 NavigationBar 高度
     * @param view View 目标View
     * @return Int 如果存在NavigationBar则返回高度，否则0
     */
    @JvmStatic
    fun getNavBarHeight(view: View): Int {
        val activity: Activity? = UtilKActivity.getActivityByView(view)
        if (activity != null) {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val usableHeight = size.y
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(size) // getRealMetrics is only available with API 17 and +
            } else {
                try {
                    size.x = (Display::class.java.getMethod("getRawWidth").invoke(display) as Int)
                    size.y = (Display::class.java.getMethod("getRawHeight").invoke(display) as Int)
                } catch (e: Exception) {
                    Log.e(TAG, "getNavBarHeight: error", e)
                }
            }
            val realHeight = size.y
            return if (realHeight > usableHeight) realHeight - usableHeight else 0
        }
        return 0
    }

    /**
     * 设置屏幕亮度
     * @param paramFloat Float 0-1范围
     * @param activity Activity
     */
    @JvmStatic
    fun setScreenBrightness(paramFloat: Float, activity: Activity) {
        val localWindow: Window = activity.window
        val params: WindowManager.LayoutParams = localWindow.attributes
        params.screenBrightness = paramFloat
        localWindow.attributes = params
    }

    /**
     * 获取屏幕密度dp
     * @return Int
     */
    @JvmStatic
    fun getDensityDpi(): Int = _context.resources.configuration.densityDpi

    /**
     * 获取屏幕密度
     * @return Float
     */
    @JvmStatic
    fun getDensity(): Float = _context.resources.displayMetrics.density

    /**
     * 获取状态栏高度1
     * 优点: 不需要在Activity的回调方法onWindowFocusChanged()执行后才能得到结果
     * 缺点: 不管你是否设置全屏模式,或是不是显示状态栏,高度是固定的;因为系统资源属性是固定的,真实的,不管你是否隐藏(隐藏或显示),他都在nali
     * @return Int
     */
    @JvmStatic
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
    @JvmStatic
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
    @JvmStatic
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
    @JvmStatic
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
    @JvmStatic
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
     * 获取屏幕尺寸
     * @return Float
     */
    @JvmStatic
    fun getScreenSize(): Float {
        val xdpi = _context.resources.displayMetrics.xdpi
        val ydpi = _context.resources.displayMetrics.ydpi
        val width = _context.resources.displayMetrics.widthPixels
        val height = _context.resources.displayMetrics.heightPixels

        //计算屏幕的物理尺寸
        val widthPhy = (width / xdpi) * (width / xdpi)
        val heightPhy = (height / ydpi) * (height / ydpi)
        return sqrt((widthPhy + heightPhy).toDouble()).toFloat()
    }

    /**
     * 获取dp宽
     * @return Int
     */
    @JvmStatic
    fun getScreenWidthDp(): Int = _context.resources.configuration.screenWidthDp

    /**
     * 获取dp高
     * @return Int
     */
    @JvmStatic
    fun getScreenHeightDp(): Int = _context.resources.configuration.screenHeightDp

    /**
     * 获取虚拟功能键的高度
     * @param context Context
     * @return Int
     */
    @JvmStatic
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
            Log.e(TAG, "getVirtualBarHeight Exception ${e.message}")
        }
        return virtualBarHeight
    }

    /**
     * 是否为竖屏
     * @return Boolean
     */
    @JvmStatic
    fun isPortrait(): Boolean = _context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    /**
     * 获取方向
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getRotation(activity: Activity): Int = UtilKDisplay.getRotation(activity)

    /**
     * 截屏
     * @param activity Activity
     * @return Bitmap
     */
    @JvmStatic
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
}