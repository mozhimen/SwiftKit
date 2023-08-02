package com.mozhimen.uicorek.adaptk.systembar.helpers

import android.app.Activity
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.basick.utilk.android.view.UtilKStatusBarIcon
import com.mozhimen.basick.utilk.android.view.UtilKSystemBar

/**
 * @ClassName SenseKSystemBarHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 15:34
 * @Version 1.0
 */
internal object AdaptKSystemBarHelper {
    @JvmStatic
    fun setSystemBarProperty(
        activity: Activity,
        isStatusBarBgTranslucent: Boolean,
        isStatusBarIconLowProfile: Boolean,
    ) {
        if (isStatusBarBgTranslucent) UtilKSystemBar.applyTranslucent(activity)
        if (isStatusBarIconLowProfile) UtilKSystemBar.applyStatusBarLowProfile(activity)
    }

    @JvmStatic
    fun setSystemBarTheme(
        activity: Activity,
        isThemeCustom: Boolean,
        isThemeDark: Boolean
    ) {
        if (isThemeCustom) UtilKStatusBarIcon.applyIcon(activity, isThemeDark)
        else UtilKStatusBarIcon.applyIcon(activity, UtilKConfiguration.isDarkMode())
    }

    @JvmStatic
    fun hideSystemBar(
        activity: Activity,
        isStatusBarHide: Boolean,
        isNavigationBarHide: Boolean,
        isTitleBarHide: Boolean,
        isActionBarHide: Boolean
    ) {
        if (isStatusBarHide) UtilKSystemBar.hideStatusBar(activity)
        if (isNavigationBarHide) UtilKSystemBar.hideNavigationBar(activity)
        if (isTitleBarHide) UtilKSystemBar.hideTitleBar(activity)
        if (isActionBarHide) UtilKSystemBar.hideActionBar(activity)
    }

    @JvmStatic
    fun overlaySystemBar(
        activity: Activity,
        isStatusBarOverlay: Boolean,
        isNavigationBarOverlay: Boolean
    ) {
        if (isStatusBarOverlay) UtilKSystemBar.overlayStatusBar(activity)
        if (isNavigationBarOverlay) UtilKSystemBar.overlayNavigationBar(activity)
    }

    @JvmStatic
    fun setLayoutProperty(
        activity: Activity,
        isLayoutStable: Boolean,
        isFitsSystemWindows: Boolean
    ) {
        if (isLayoutStable) UtilKSystemBar.applyLayoutStable(activity)
        if (isFitsSystemWindows) UtilKSystemBar.applyFitsSystemWindows(activity)
    }

    @JvmStatic
    fun setImmersed(activity: Activity, isImmersedHard: Boolean = false, isImmersedSticky: Boolean = false) {
        if (isImmersedHard) UtilKSystemBar.applyImmersedHard(activity)
        if (isImmersedSticky) UtilKSystemBar.applyImmersedSticky(activity)
    }
}