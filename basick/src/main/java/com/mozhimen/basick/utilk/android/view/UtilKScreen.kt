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

    //获取屏幕密度dp
    @JvmStatic
    fun getDensityDpi_ofSysMetrics(): Int =
        UtilKDisplayMetrics.getSysDensityDpi()

    @JvmStatic
    fun getDensityDpi_ofSysConfig(): Int =
        UtilKConfiguration.getSysDensityDpi()

    /////////////////////////////////////////////////////////////////////

    //获取屏幕密度
    @JvmStatic
    fun getDensity_ofSysMetrics(): Float =
        UtilKDisplayMetrics.getSysDensity()

    /////////////////////////////////////////////////////////////////////

    //获取屏幕宽度
    @JvmStatic
    fun getWidth(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKWindowMetrics.getCurBoundsWidth(_context)
        else
            UtilKDisplay.getDefSizeX(_context)

    @JvmStatic
    @ADescription("getCurrentWidth","getWidthOfDisplay")
    fun getWidth_ofSysMetrics(): Int =
        UtilKDisplayMetrics.getSysWidthPixels()

    @JvmStatic
    fun getWidth_ofDefMetrics(): Int =
        UtilKDisplayMetrics.getDefWidthPixels(_context)

    @JvmStatic
    fun getWidth_ofRealMetrics(): Int =
        UtilKDisplayMetrics.getRealWidthPixels(_context)

    @JvmStatic
    fun getWidth_ofDefDisplay(): Int =
        UtilKDisplay.getDefWidth(_context)

    @JvmStatic
    fun getWidth_ofSizeDefDisplay(): Int =
        UtilKDisplay.getDefSizeX(_context)

    @JvmStatic
    fun getWidth_ofRealSizeDefDisplay(): Int =
        UtilKDisplay.getDefRealSizeX(_context)

    /////////////////////////////////////////////////////////////////////

    //获取屏幕高度
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
    fun getHeight_ofSysMetrics(): Int =
        UtilKDisplayMetrics.getSysHeightPixels()

    /**
     * 获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
     */
    @JvmStatic
    fun getHeight_ofDefMetrics(): Int =
        UtilKDisplayMetrics.getDefHeightPixels(_context)

    @JvmStatic
    fun getHeight_ofRealMetrics(): Int =
        UtilKDisplayMetrics.getRealHeightPixels(_context)

    @JvmStatic
    fun getHeight_ofDefDisplay(): Int =
        UtilKDisplay.getDefHeight(_context)

    @JvmStatic
    fun getHeight_ofSizeDefDisplay(): Int =
        UtilKDisplay.getDefSizeY(_context)

    @JvmStatic
    fun getHeight_ofRealSizeDefDisplay(): Int =
        UtilKDisplay.getDefRealSizeY(_context)

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取屏幕尺寸
     */
    @JvmStatic
    fun getSize_ofSysMetrics(): Float =
        UtilKDisplayMetrics.getSysSize()

    /////////////////////////////////////////////////////////////////////

    //获取dp宽
    @JvmStatic
    fun getWidthDp_ofSysConfig(): Int =
        UtilKConfiguration.getSysScreenWidthDp()

    @JvmStatic
    fun getHeightDp_ofSysConfig(): Int =
        UtilKConfiguration.getSysScreenHeightDp()

    /////////////////////////////////////////////////////////////////////

    //获取屏幕方向(横屏,竖屏)
    @JvmStatic
    fun getOrientation_ofSysConfig(): Int =
        UtilKConfiguration.getSysOrientation()

    //获取屏幕方向(更加推荐用这种方式)
    @JvmStatic
    fun getOrientation_ofDefDisplay(context: Context): Int =
        UtilKDisplay.getDefOrientation(context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //获取旋转角度
    @JvmStatic
    fun getRotation(activity: Activity): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKDisplay.getAppRotation(activity)
        else
            UtilKDisplay.getDefRotation(activity as Context)

    @JvmStatic
    fun getRotation_ofDefDisplay(context: Context): Int =
        UtilKDisplay.getDefRotation(context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //是否为竖屏(系统配置)
    @JvmStatic
    fun isOrientationPortrait_ofSysConfig(): Boolean =
        UtilKConfiguration.isSysOrientationPortrait()

    //是否为竖屏(更为准确)
    @JvmStatic
    fun isOrientationPortrait_ofDefDisplay(context: Context): Boolean =
        UtilKDisplay.isDefOrientationPortrait(context)

    //是否为竖屏(通过宽高的方式)
    @JvmStatic
    fun isOrientationPortrait_ofSysMetrics(): Boolean =
        UtilKDisplayMetrics.isSysOrientationPortrait()

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isOrientationLandscape_ofSysConfig(): Boolean =
        UtilKConfiguration.isSysOrientationLandscape()

    @JvmStatic
    fun isOrientationLandscape_ofDefDisplay(context: Context): Boolean =
        UtilKDisplay.isDefOrientationLandscape(context)

    @JvmStatic
    fun isOrientationLandscape_ofSysMetrics(): Boolean =
        UtilKDisplayMetrics.isSysOrientationLandscape()

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreen_ofWindow(activity: Activity): Boolean =
        UtilKWindowParams.isFullScreen(activity)

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreen_ofTheme(): Boolean =
        UtilKTheme.isFullScreen(_context)

//        /**
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

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //是否使屏幕常亮
    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean) {
        UtilKWindowWrapper.applyKeepScreen(activity, isKeepScreenOn)
    }

    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean, isMaxBrightness: Boolean) {
        UtilKWindowWrapper.applyKeepScreen(activity, isKeepScreenOn, isMaxBrightness)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //截屏
    @JvmStatic
    fun applyCapture(activity: Activity): Bitmap =
        UtilKDecorView.getBitmapForDrawingCache(activity)
}