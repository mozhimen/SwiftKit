package com.mozhimen.basick.utilk.wrapper

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.basick.utilk.android.content.UtilKTheme
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import com.mozhimen.basick.utilk.android.view.UtilKDisplay
import com.mozhimen.basick.utilk.android.view.UtilKWindowManagerLayoutParams
import com.mozhimen.basick.utilk.android.view.UtilKWindowMetrics
import com.mozhimen.basick.utilk.android.view.UtilKWindowWrapper

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
        UtilKDisplayMetrics.getDensityDpi_ofSys()

    @JvmStatic
    fun getDensityDpi_ofSysConfig(): Int =
        UtilKConfiguration.getDensityDpi_ofSys()

    /////////////////////////////////////////////////////////////////////

    //获取屏幕密度
    @JvmStatic
    fun getDensity_ofSysMetrics(): Float =
        UtilKDisplayMetrics.getDensity_ofSys()

    /////////////////////////////////////////////////////////////////////

    //获取屏幕宽度
    @JvmStatic
    fun getWidth(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            getWidth_ofWindowMetrics_ofCur()
        else
            getWidth_ofDisplay_ofDefSize()

    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getWidth_ofWindowMetrics_ofCur(): Int =
        UtilKWindowMetrics.getBoundsWidth_ofCur(_context)

    @JvmStatic
    @ADescription("getCurrentWidth", "getWidthOfDisplay")
    fun getWidth_ofDisplayMetrics_ofSys(): Int =
        UtilKDisplayMetrics.getWidthPixels_ofSys()

    @JvmStatic
    fun getWidth_ofDisplayMetrics_ofApp(): Int =
        UtilKDisplayMetrics.getWidthPixels_ofApp(_context)

    @JvmStatic
    fun getWidth_ofDisplayMetrics_ofDef(): Int =
        UtilKDisplayMetrics.getWidthPixels_ofDef(_context)

    @JvmStatic
    fun getWidth_ofDisplayMetrics_ofReal(): Int =
        UtilKDisplayMetrics.getWidthPixels_ofReal(_context)

    @JvmStatic
    fun getWidth_ofDisplay_ofDef(): Int =
        UtilKDisplay.getWidth_ofDef(_context)

    @JvmStatic
    fun getWidth_ofDisplay_ofDefSize(): Int =
        UtilKDisplay.getSizeX_ofDef(_context)

    @JvmStatic
    fun getWidth_ofDisplay_ofDefSizeReal(): Int =
        UtilKDisplay.getRealSizeX_ofDef(_context)

    /////////////////////////////////////////////////////////////////////

    //获取屏幕高度
    @JvmStatic
    fun getHeight(): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKWindowMetrics.getBoundsHeight_ofCur(_context)
        else
            UtilKDisplay.getSizeY_ofDef(_context)

    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getHeight_ofWindowMetrics_ofCur(): Int =
        UtilKWindowMetrics.getBoundsHeight_ofCur(_context)

    //获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
    @JvmStatic
    fun getHeight_ofDisplayMetrics_ofSys(): Int =
        UtilKDisplayMetrics.getHeightPixels_ofSys()

    @JvmStatic
    fun getHeight_ofDisplayMetrics_ofApp(): Int =
        UtilKDisplayMetrics.getHeightPixels_ofApp(_context)

    //获取屏幕高度 2的和1的区别是是否考虑状态栏等, 2是减去状态栏的高度, 即为当前的
    @JvmStatic
    fun getHeight_ofDisplayMetrics_ofDef(): Int =
        UtilKDisplayMetrics.getHeightPixels_ofDef(_context)

    @JvmStatic
    fun getHeight_ofDisplayMetrics_ofReal(): Int =
        UtilKDisplayMetrics.getHeightPixels_ofReal(_context)

    @JvmStatic
    fun getHeight_ofDisplay_ofDef(): Int =
        UtilKDisplay.getHeight_ofDef(_context)

    @JvmStatic
    fun getHeight_ofDisplay_ofDefSize(): Int =
        UtilKDisplay.getSizeY_ofDef(_context)

    @JvmStatic
    fun getHeight_ofDisplay_ofDefSizeReal(): Int =
        UtilKDisplay.getRealSizeY_ofDef(_context)

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取屏幕尺寸
     */
    @JvmStatic
    fun getSize_ofSysMetrics(): Float =
        UtilKDisplayMetrics.getSize_ofSys()

    /////////////////////////////////////////////////////////////////////

    //获取dp宽
    @JvmStatic
    fun getWidthDp_ofSysConfig(): Int =
        UtilKConfiguration.getScreenWidthDp_ofSys()

    @JvmStatic
    fun getHeightDp_ofSysConfig(): Int =
        UtilKConfiguration.getScreenHeightDp_ofSys()

    /////////////////////////////////////////////////////////////////////

    //获取屏幕方向(横屏,竖屏)
    @JvmStatic
    fun getOrientation_ofSysConfig(): Int =
        UtilKConfiguration.getOrientation_ofSys()

    //获取屏幕方向(更加推荐用这种方式)
    @JvmStatic
    fun getOrientation_ofDefDisplay(context: Context): Int =
        UtilKDisplay.getOrientation_ofDef(context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //获取旋转角度
    @JvmStatic
    fun getRotation(activity: Activity): Int =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKDisplay.getRotation_ofApp(activity)
        else
            UtilKDisplay.getRotation_ofDef(activity as Context)

    @JvmStatic
    fun getRotation_ofDefDisplay(context: Context): Int =
        UtilKDisplay.getRotation_ofDef(context)

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //是否为竖屏(系统配置)
    @JvmStatic
    fun isOrientationPortrait_ofSysConfig(): Boolean =
        UtilKConfiguration.isOrientationPortrait_ofSys()

    //是否为竖屏(更为准确)
    @JvmStatic
    fun isOrientationPortrait_ofDefDisplay(context: Context): Boolean =
        UtilKDisplay.isOrientationPortrait_ofDef(context)

    //是否为竖屏(通过宽高的方式)
    @JvmStatic
    fun isOrientationPortrait_ofSysMetrics(): Boolean =
        UtilKDisplayMetrics.isOrientationPortrait_ofSys()

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isOrientationLandscape_ofSysConfig(): Boolean =
        UtilKConfiguration.isOrientationLandscape_ofSys()

    @JvmStatic
    fun isOrientationLandscape_ofDefDisplay(context: Context): Boolean =
        UtilKDisplay.isOrientationLandscape_ofDef(context)

    @JvmStatic
    fun isOrientationLandscape_ofSysMetrics(): Boolean =
        UtilKDisplayMetrics.isOrientationLandscape_ofSys()

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreen_ofWindow(activity: Activity): Boolean =
        UtilKWindowManagerLayoutParams.isFullScreen(activity)

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
        UtilKWindowManagerLayoutParams.applyBrightness(activity, paramFloat)
    }

    @JvmStatic
    fun applyMaxBrightness(activity: Activity, isMaxBrightness: Boolean) {
        UtilKWindowManagerLayoutParams.applyMaxBrightness(activity, isMaxBrightness)
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