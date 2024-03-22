package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr

/**
 * @ClassName UtilKWindowManagerLayoutParams
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/22
 * @Version 1.0
 */
object UtilKWindowManagerLayoutParams {
    @JvmStatic
    fun get(activity: Activity): WindowManager.LayoutParams =
        UtilKWindow.getAttributes(activity)

    @JvmStatic
    fun get(window: Window): WindowManager.LayoutParams =
        UtilKWindow.getAttributes(window)

    ///////////////////////////////////////////////////////////////

    @JvmStatic
    fun getFlags(window: Window): Int =
        get(window).flags

    @JvmStatic
    fun getFlags(activity: Activity): Int =
        get(activity).flags

    ///////////////////////////////////////////////////////////////

    @JvmStatic
    fun isFlagTranslucentStatus(activity: Activity): Boolean =
        getFlags(activity) and CWinMgr.Lpf.TRANSLUCENT_STATUS != 0

    @JvmStatic
    fun isFlagFullScreen(activity: Activity): Boolean =
        getFlags(activity) and CWinMgr.Lpf.FULLSCREEN != 0

    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        isFlagFullScreen(activity) || !UtilKNavigationBar.isVisible(activity) || !UtilKStatusBar.isVisible(activity)
}