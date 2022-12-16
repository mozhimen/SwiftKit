package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basick.utilk.bar.UtilKVirtualBar
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

    private val _context = UtilKApplication.instance.get()

    /**
     * 设置全屏
     * @param view View
     */
    @JvmStatic
    fun setFullScreen(view: View) {
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
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
    fun getScreenDensityDpi(): Int = _context.resources.configuration.densityDpi

    /**
     * 获取屏幕密度
     * @return Float
     */
    @JvmStatic
    fun getScreenDensity(): Float = _context.resources.displayMetrics.density

    /**
     * 获取屏幕宽度
     * @return Int
     */
    @JvmStatic
    fun getCurrentScreenWidth(): Int {
        return _context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     * @return Int
     */
    @JvmStatic
    fun getCurrentScreenHeight(): Int {
        return _context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取像素宽
     * @return Int
     */
    @JvmStatic
    fun getRealScreenWidth(): Int =
        if (Build.VERSION.SDK_INT >= UtilKBuild.VersionCode.R) {
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
    fun getRealScreenHeight(): Int =
        if (Build.VERSION.SDK_INT >= UtilKBuild.VersionCode.R) {
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
        val width = getCurrentScreenWidth()
        val height = getCurrentScreenHeight()

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
    fun getScreenWidthDp(): Int =
        _context.resources.configuration.screenWidthDp

    /**
     * 获取dp高
     * @return Int
     */
    @JvmStatic
    fun getScreenHeightDp(): Int =
        _context.resources.configuration.screenHeightDp

    /**
     * 获取屏幕方向
     * @return Int
     */
    @JvmStatic
    fun getScreenOrientation(): Int =
        _context.resources.configuration.orientation

    /**
     * 是否为竖屏
     * @return Boolean
     */
    @JvmStatic
    fun isScreenPortrait(): Boolean =
        getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT

    /**
     * 获取方向
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getScreenRotation(activity: Activity): Int =
        UtilKDisplay.getRotation(activity)

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
        val bitmap = Bitmap.createBitmap(view.drawingCache, 0, 0, view.measuredWidth, view.measuredHeight - UtilKVirtualBar.getVirtualBarHeight(activity))
        view.isDrawingCacheEnabled = false
        view.destroyDrawingCache()
        return bitmap
    }
}