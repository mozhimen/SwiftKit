package com.mozhimen.basick.sensek.systembar.helpers

import android.app.Activity
import com.mozhimen.basick.elemk.cons.CView
import com.mozhimen.basick.utilk.android.view.UtilKContentView
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import com.mozhimen.basick.utilk.android.view.UtilKStatusBarIcon
import com.mozhimen.basick.utilk.android.view.UtilKSystemBar

/**
 * @ClassName SenseKSystemBarHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 15:34
 * @Version 1.0
 */
object SenseKSystemBarHelper {
    @JvmStatic
    fun setSystemBarLowProfile(activity: Activity) {
        UtilKSystemBar.setStatusBarLowProfile(activity)
    }

    @JvmStatic
    fun setSystemBarTheme(activity: Activity, isThemeDark: Boolean) {
        UtilKStatusBarIcon.setIcon(activity, isThemeDark)
    }

    @JvmStatic
    fun hideSystemBar(
        activity: Activity,
        isStatusBarHide: Boolean = false,
        isNavigationBarHide: Boolean = false,
        isTitleBarHide: Boolean = false,
        isActionBarHide: Boolean = false
    ) {
        if (isStatusBarHide) UtilKSystemBar.hideStatusBar(activity)
        if (isNavigationBarHide) UtilKSystemBar.hideNavigationBar(activity)
        if (isTitleBarHide) UtilKSystemBar.hideTitleBar(activity)
        if (isActionBarHide) UtilKSystemBar.hideActionBar(activity)
    }

    @JvmStatic
    fun overlaySystemBar(
        activity: Activity,
        isStatusBarOverlay: Boolean = false,
        isNavigationBarOverlay: Boolean = false,
    ) {
        if (isStatusBarOverlay) UtilKSystemBar.overlayStatusBar(activity)
        if (isNavigationBarOverlay) UtilKSystemBar.overlayNavigationBar(activity)
    }

    @JvmStatic
    fun setLayoutStable(activity: Activity) {
        UtilKSystemBar.setLayoutStable(activity)
    }

    @JvmStatic
    fun setFitsSystemWindows(activity: Activity) {
        UtilKSystemBar.setFitsSystemWindows(activity)
    }

    @JvmStatic
    fun setImmersed(activity: Activity, isImmersedHard: Boolean = false, isImmersedSticky: Boolean = false) {
        if (isImmersedHard) UtilKSystemBar.setImmersedHard(activity)
        if (isImmersedSticky) UtilKSystemBar.setImmersedSticky(activity)
    }
}