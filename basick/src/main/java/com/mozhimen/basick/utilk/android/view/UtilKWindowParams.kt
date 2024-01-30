package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.Window
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr

/**
 * @ClassName UtilKWindowParams
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 11:17
 * @Version 1.0
 */
object UtilKWindowParams {
    @JvmStatic
    fun getAttributesFlags(activity: Activity): Int =
        getAttributesFlags(UtilKWindow.get(activity))

    @JvmStatic
    fun getAttributesFlags(window: Window): Int =
        UtilKWindow.getAttributes(window).flags

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isFlagStatusBarTranslucent(activity: Activity): Boolean =
        getAttributesFlags(activity) and CWinMgr.Lpf.TRANSLUCENT_STATUS != 0

    @JvmStatic
    fun isFlagFullScreen(activity: Activity): Boolean =
        getAttributesFlags(activity) and CWinMgr.Lpf.FULLSCREEN != 0

    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        isFlagFullScreen(activity) || !UtilKNavigationBar.isVisible(activity) || !UtilKStatusBar.isVisible(activity)
}