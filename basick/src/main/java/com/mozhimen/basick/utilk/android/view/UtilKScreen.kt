package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.WindowManager
import androidx.annotation.FloatRange
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapDeal
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.basick.utilk.android.content.UtilKTheme
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics
import kotlin.math.sqrt

/**
 * @ClassName ScreenUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/21 9:16
 * @Version 1.0
 */
object UtilKScreen : BaseUtilK() {

    /**
     * 是否全屏
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        UtilKWindow.isFullScreen(activity)

    /**
     * 是否全屏
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreen(): Boolean =
        UtilKTheme.isFullScreen(_context)

//    /**
//     * 设置全屏
//     * @param decorView View
//     */
//    @JvmStatic
//    fun setFullScreen(decorView: View) {
//        UtilKDecorView.setFullScreen(decorView)
//    }

    /**
     * 设置屏幕亮度
     * @param paramFloat Float 0-1范围
     * @param activity Activity
     */
    @JvmStatic
    fun setBrightness(@FloatRange(from = 0.0, to = 1.0) paramFloat: Float, activity: Activity) {
        val layoutParams: WindowManager.LayoutParams = UtilKWindow.getAttributes(activity)
        layoutParams.screenBrightness = paramFloat
        UtilKWindow.setAttributes(activity, layoutParams)
    }

    ////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取屏幕密度dp
     * @return Int
     */
    @JvmStatic
    fun getDensityDpi(): Int =
        UtilKDisplayMetrics.getDensityDpi()

    /**
     * 获取屏幕密度dp
     * @return Int
     */
    @JvmStatic
    fun getDensityDpi1(): Int =
        UtilKConfiguration.getDensityDpi()

    /**
     * 获取屏幕密度
     * @return Float
     */
    @JvmStatic
    fun getDensity(): Float =
        UtilKDisplayMetrics.getDensity()

    /**
     * 获取屏幕宽度
     * @return Int
     */
    @JvmStatic
    fun getCurrentWidth(): Int =
        UtilKDisplayMetrics.getWidthPixels()

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     * @return Int
     */
    @JvmStatic
    fun getCurrentHeight(): Int =
        UtilKDisplayMetrics.getHeightPixels()

    /**
     * 获取像素宽
     * @return Int
     */
    @JvmStatic
    fun getRealWidth(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            UtilKWindowManager.getBoundsWidth(_context)
        } else {
            UtilKWindowManager.getSizeX(_context)
        }

    /**
     * 获取像素高
     * @return Int
     */
    @JvmStatic
    fun getRealHeight(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            UtilKWindowManager.getBoundsHeight(_context)
        } else {
            UtilKWindowManager.getSizeY(_context)
        }

    /**
     * 获取屏幕尺寸
     * @return Float
     */
    @JvmStatic
    fun getSize(): Float {
        val xdpi = UtilKDisplayMetrics.getXdpi()
        val ydpi = UtilKDisplayMetrics.getYdpi()
        val width = getCurrentWidth()
        val height = getCurrentHeight()
        //计算屏幕的物理尺寸
        val widthPhy = (width / xdpi) * (width / xdpi)
        val heightPhy = (height / ydpi) * (height / ydpi)
        return sqrt(widthPhy + heightPhy)
    }

    /**
     * 获取dp宽
     * @return Int
     */
    @JvmStatic
    fun getWidthDp(): Int =
        UtilKConfiguration.getScreenWidthDp()

    /**
     * 获取dp高
     * @return Int
     */
    @JvmStatic
    fun getHeightDp(): Int =
        UtilKConfiguration.getScreenHeightDp()

    /**
     * 获取屏幕方向
     * @return Int
     */
    @JvmStatic
    fun getOrientation(): Int =
        UtilKConfiguration.getOrientation()

    /**
     * 是否为竖屏
     * @return Boolean
     */
    @JvmStatic
    fun isOrientationPortrait(): Boolean =
        UtilKConfiguration.isOrientationPortrait()

    @JvmStatic
    fun isOrientationLandscape(): Boolean =
        UtilKConfiguration.isOrientationLandscape()

    /**
     * 获取方向
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getRotation(activity: Activity): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            UtilKDisplay.getRotation(activity)
        } else {
            UtilKDisplay.getDefaultRotation(activity as Context)
        }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 截屏
     * @param activity Activity
     * @return Bitmap
     */
    @JvmStatic
    fun capture(activity: Activity): Bitmap =
        UtilKBitmapDeal.getBitmapForScreen(activity)
}