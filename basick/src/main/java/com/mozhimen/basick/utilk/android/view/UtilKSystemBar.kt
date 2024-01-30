package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import com.mozhimen.basick.utilk.androidx.appcompat.UtilKActionBar

/**
 * @ClassName UtilKSystemBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/28 17:12
 * @Version 1.0
 */
object UtilKSystemBar {
    @JvmStatic
    fun applyStatusBarLowProfile(activity: Activity) {
        UtilKStatusBarIcon.applyIconLowProfile(activity)
    }

    @JvmStatic
    fun applyTranslucent(activity: Activity){
        applyStatusBarTranslucent(activity)
        applyNavigationBarTranslucent(activity)
    }

    @JvmStatic
    fun applyStatusBarTranslucent(activity: Activity) {
        UtilKStatusBar.applyTranslucent(activity)
    }

    @JvmStatic
    fun applyNavigationBarTranslucent(activity: Activity) {
        UtilKNavigationBar.applyTranslucent(activity)
    }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyLayoutStable(activity: Activity) {
        UtilKDecorView.applyLayoutStable(activity)
    }

    @JvmStatic
    fun applyFitsSystemWindows(activity: Activity) {
        UtilKDecorView.applyFitsSystemWindows(activity)
    }

    @JvmStatic
    fun applyImmersedHard(activity: Activity) {
        UtilKDecorView.applyImmersedHard(activity)
    }

    @JvmStatic
    fun applyImmersedSticky(activity: Activity) {
        UtilKDecorView.applyImmersedSticky(activity)
    }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hideTitleBar(activity: Activity) {
        UtilKTitleBar.hide(activity)
    }

    @JvmStatic
    fun hideActionBar(activity: Activity) {
        UtilKActionBar.hide(activity)
    }

    @JvmStatic
    fun hideStatusBar(activity: Activity) {
        UtilKStatusBar.hide(activity)
    }

    @JvmStatic
    fun hideNavigationBar(activity: Activity) {
        UtilKNavigationBar.hide(activity)
    }

    @JvmStatic
    fun overlayStatusBar(activity: Activity) {
        UtilKStatusBar.overlay(activity)
    }

    @JvmStatic
    fun overlayNavigationBar(activity: Activity) {
        UtilKNavigationBar.overlay(activity)
    }
}