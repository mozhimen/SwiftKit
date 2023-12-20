package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.WindowManager
import androidx.annotation.FloatRange
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.utilk.bases.BaseUtilK
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
     * 获取屏幕密度dp
     */
    @JvmStatic
    fun getDensityDpi(): Int =
        UtilKDisplayMetrics.getDensityDpi()

    /**
     * 获取屏幕密度dp
     */
    @JvmStatic
    fun getDensityDpi1(): Int =
        UtilKConfiguration.getDensityDpi()

    /**
     * 获取屏幕密度
     */
    @JvmStatic
    fun getDensity(): Float =
        UtilKDisplayMetrics.getDensity()

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    fun getWidthOfDisplay(): Int =
        UtilKDisplayMetrics.getWidthPixels()

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     */
    @JvmStatic
    fun getHeightOfDisplay(): Int =
        UtilKDisplayMetrics.getHeightPixels()

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    fun getWidthOfDefaultDisplay(): Int =
        UtilKDisplayMetrics.getOfDefaultWidthPixels(_context)

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     */
    @JvmStatic
    fun getHeightOfDefaultDisplay(): Int =
        UtilKDisplayMetrics.getOfDefaultHeightPixels(_context)

    /**
     * 获取像素宽
     */
    @JvmStatic
    fun getWidthOfWindow(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKWindowManager.getBoundsWidth(_context)
        else
            UtilKWindowManager.getSizeX(_context)

    /**
     * 获取像素高
     */
    @JvmStatic
    fun getHeightOfWindow(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKWindowManager.getBoundsHeight(_context)
        else
            UtilKWindowManager.getSizeY(_context)

    /**
     * 获取屏幕尺寸
     */
    @JvmStatic
    fun getSize(): Float {
        val xdpi = UtilKDisplayMetrics.getXdpi()
        val ydpi = UtilKDisplayMetrics.getYdpi()
        val width = getWidthOfDisplay()
        val height = getHeightOfDisplay()
        //计算屏幕的物理尺寸
        val widthPhy = (width / xdpi) * (width / xdpi)
        val heightPhy = (height / ydpi) * (height / ydpi)
        return sqrt(widthPhy + heightPhy)
    }

    /**
     * 获取dp宽
     */
    @JvmStatic
    fun getWidthDp(): Int =
        UtilKConfiguration.getScreenWidthDp()

    /**
     * 获取dp高
     */
    @JvmStatic
    fun getHeightDp(): Int =
        UtilKConfiguration.getScreenHeightDp()

    /**
     * 获取屏幕方向
     */
    @JvmStatic
    fun getOrientation(): Int =
        UtilKConfiguration.getOrientation()


    /**
     * 获取方向
     */
    @JvmStatic
    fun getRotation(activity: Activity): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKDisplay.getRotation(activity)
        else
            UtilKDisplay.getDefaultRotation(activity as Context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否为竖屏
     */
    @JvmStatic
    fun isOrientationPortrait(): Boolean =
        UtilKConfiguration.isOrientationPortrait()

    @JvmStatic
    fun isOrientationLandscape(): Boolean =
        UtilKConfiguration.isOrientationLandscape()

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        UtilKWindow.isFullScreen(activity)

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreen(): Boolean =
        UtilKTheme.isFullScreen(_context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置屏幕亮度
     * @param paramFloat Float 0-1范围
     */
    @JvmStatic
    fun applyBrightness(activity: Activity, @FloatRange(from = 0.0, to = 1.0) paramFloat: Float) {
        val layoutParams: WindowManager.LayoutParams = UtilKWindow.getAttributes(activity)
        layoutParams.screenBrightness = paramFloat
        UtilKWindow.applyAttributes(activity, layoutParams)
    }

    @JvmStatic
    fun applyMaxBrightness(activity: Activity, isMaxBrightness: Boolean) {
        val layoutParams: WindowManager.LayoutParams = UtilKWindow.getAttributes(activity)
        layoutParams.screenBrightness = if (isMaxBrightness) CWinMgr.Lp.BRIGHTNESS_OVERRIDE_FULL else CWinMgr.Lp.BRIGHTNESS_OVERRIDE_NONE
        UtilKWindow.applyAttributes(activity, layoutParams)
    }

    /**
     * 是否使屏幕常亮
     */
    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean) {
        if (isKeepScreenOn)
            UtilKWindow.addFlags(activity, CWinMgr.Lpf.KEEP_SCREEN_ON)
        else
            UtilKWindow.clearFlags(activity, CWinMgr.Lpf.KEEP_SCREEN_ON)
    }

    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean, isMaxBrightness: Boolean) {
        applyKeepScreen(activity, isKeepScreenOn)
        applyMaxBrightness(activity, isMaxBrightness)
    }

    /**
     * 截屏
     */
    @JvmStatic
    fun applyCapture(activity: Activity): Bitmap =
        UtilKDecorView.getBitmapForDrawingCache(activity)

//    /**
//     * 设置全屏
//     * @param decorView View
//     */
//    @JvmStatic
//    fun applyFullScreen(decorView: View) {
//        UtilKDecorView.setFullScreen(decorView)
//    }
}