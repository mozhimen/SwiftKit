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
    fun setStatusBarLowProfile(activity: Activity) {
        UtilKStatusBarIcon.setLowProfile(activity)
    }

    @JvmStatic
    fun setTranslucent(activity: Activity){
        setStatusBarTranslucent(activity)
        setNavigationBarTranslucent(activity)
    }

    @JvmStatic
    fun setStatusBarTranslucent(activity: Activity) {
        UtilKStatusBar.setTranslucent(activity)
    }

    @JvmStatic
    fun setNavigationBarTranslucent(activity: Activity) {
        UtilKNavigationBar.setTranslucent(activity)
    }

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

    @JvmStatic
    fun setLayoutStable(activity: Activity) {
        UtilKDecorView.setLayoutStable(activity)
    }

    @JvmStatic
    fun setFitsSystemWindows(activity: Activity) {
        UtilKContentView.setFitsSystemWindows(activity)
    }

    @JvmStatic
    fun setImmersedHard(activity: Activity) {
        UtilKDecorView.setImmersedHard(activity)
    }

    @JvmStatic
    fun setImmersedSticky(activity: Activity) {
        UtilKDecorView.setImmersedSticky(activity)
    }
}