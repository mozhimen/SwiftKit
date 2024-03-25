package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.Window
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
        UtilKWindowManagerLayoutParams.applyMaxBrightness(activity, isMaxBrightness)
    }
}