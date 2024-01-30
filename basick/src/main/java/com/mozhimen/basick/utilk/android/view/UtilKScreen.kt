package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.FloatRange
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.basick.utilk.android.content.UtilKTheme
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics

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
    fun getDensityDpiOfDefDisplay(): Int =
        UtilKDisplayMetrics.getSysDensityDpi()

    /**
     * 获取屏幕密度dp
     */
    @JvmStatic
    fun getDensityDpiOfSysConfig(): Int =
        UtilKConfiguration.getSysDensityDpi()

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取屏幕密度
     */
    @JvmStatic
    fun getDensityOfSysDisplay(): Float =
        UtilKDisplayMetrics.getSysDensity()

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取像素宽
     */
    @JvmStatic
    fun getWidth(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKWindowMetrics.getCurBoundsWidth(_context)
        else
            UtilKDisplay.getDefSizeX(_context)

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    @ADescription("getCurrentWidth")
    fun getWidthOfSysDisplay(): Int =
        UtilKDisplayMetrics.getSysWidthPixels()

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    fun getWidthOfDefDisplay(): Int =
        UtilKDisplayMetrics.getDefWidthPixels(_context)

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取像素高
     */
    @JvmStatic
    fun getHeight(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKWindowMetrics.getCurBoundsHeight(_context)
        else
            UtilKDisplay.getDefSizeY(_context)

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     */
    @JvmStatic
    fun getHeightOfSysDisplay(): Int =
        UtilKDisplayMetrics.getSysHeightPixels()

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     */
    @JvmStatic
    fun getHeightOfDefDisplay(): Int =
        UtilKDisplayMetrics.getDefHeightPixels(_context)

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取屏幕尺寸
     */
    @JvmStatic
    fun getSizeOfSysDisplay(): Float =
        UtilKDisplayMetrics.getSysSize()

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取dp宽
     */
    @JvmStatic
    fun getWidthDpOfSysConfig(): Int =
        UtilKConfiguration.getSysScreenWidthDp()

    /**
     * 获取dp高
     */
    @JvmStatic
    fun getHeightDpOfSysConfig(): Int =
        UtilKConfiguration.getSysScreenHeightDp()

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取屏幕方向
     */
    @JvmStatic
    fun getOrientationOfSysConfig(): Int =
        UtilKConfiguration.getSysOrientation()

    /**
     * 获取屏幕方向(更加推荐用这种方式)
     */
    @JvmStatic
    fun getOrientationOfDefDisplay(context: Context): Int =
        UtilKDisplay.getDefOrientation(context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取方向
     */
    @JvmStatic
    fun getRotation(activity: Activity): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKDisplay.getRotation(activity)
        else
            UtilKDisplay.getDefRotation(activity as Context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否为竖屏
     */
    @JvmStatic
    fun isOrientationPortraitOfSysConfig(): Boolean =
        UtilKConfiguration.isSysOrientationPortrait()

    /**
     * 是否为竖屏(更为准确)
     */
    @JvmStatic
    fun isOrientationPortraitOfDefDisplay(context: Context): Boolean =
        UtilKDisplay.isDefOrientationPortrait(context)

    @JvmStatic
    fun isOrientationLandscapeOfSysConfig(): Boolean =
        UtilKConfiguration.isSysOrientationLandscape()

    @JvmStatic
    fun isOrientationLandscapeOfDefDisplay(context: Context): Boolean =
        UtilKDisplay.isDefOrientationLandscape(context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreenOfWindow(activity: Activity): Boolean =
        UtilKWindowParams.isFullScreen(activity)

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreenOfTheme(): Boolean =
        UtilKTheme.isFullScreen(_context)

    //    /**
//     * 设置全屏
//     * @param decorView View
//     */
//    @JvmStatic
//    fun applyFullScreen(decorView: View) {
//        UtilKDecorView.setFullScreen(decorView)
//    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置屏幕亮度
     * @param paramFloat Float 0-1范围
     */
    @JvmStatic
    fun applyBrightness(activity: Activity, @FloatRange(from = 0.0, to = 1.0) paramFloat: Float) {
        UtilKWindowWrapper.applyBrightness(activity, paramFloat)
    }

    @JvmStatic
    fun applyMaxBrightness(activity: Activity, isMaxBrightness: Boolean) {
        UtilKWindowWrapper.applyMaxBrightness(activity, isMaxBrightness)
    }

    /**
     * 是否使屏幕常亮
     */
    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean) {
        UtilKWindowWrapper.applyKeepScreen(activity, isKeepScreenOn)
    }

    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean, isMaxBrightness: Boolean) {
        UtilKWindowWrapper.applyKeepScreen(activity, isKeepScreenOn, isMaxBrightness)
    }

    /**
     * 截屏
     */
    @JvmStatic
    fun applyCapture(activity: Activity): Bitmap =
        UtilKDecorView.getBitmapForDrawingCache(activity)
}