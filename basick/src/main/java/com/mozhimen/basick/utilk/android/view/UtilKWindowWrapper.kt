package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.WindowManager
import androidx.annotation.FloatRange
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr

/**
 * @ClassName UtilKWindowWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 11:10
 * @Version 1.0
 */
object UtilKWindowWrapper {
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

    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean) {
        if (isKeepScreenOn)
            addFlagsKeepScreenOn(activity)
        else
            clearFlagsKeepScreenOn(activity)
    }

    @JvmStatic
    fun addFlagsKeepScreenOn(activity: Activity) {
        UtilKWindow.addFlags(activity, CWinMgr.Lpf.KEEP_SCREEN_ON)
    }

    @JvmStatic
    fun clearFlagsKeepScreenOn(activity: Activity) {
        UtilKWindow.clearFlags(activity, CWinMgr.Lpf.KEEP_SCREEN_ON)
    }

    @JvmStatic
    fun applyKeepScreen(activity: Activity, isKeepScreenOn: Boolean, isMaxBrightness: Boolean) {
        applyKeepScreen(activity, isKeepScreenOn)
        applyMaxBrightness(activity, isMaxBrightness)
    }
}