package com.mozhimen.basick.sensek.systembar

import android.app.Activity
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBar
import com.mozhimen.basick.sensek.systembar.cons.CSystemBarType
import com.mozhimen.basick.sensek.systembar.helpers.SenseKSystemBarHelper
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.basick.utilk.android.view.UtilKSystemBar

/**
 * @ClassName StatusBarK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
/**
 * 作用: 状态栏管理
 * 用法:
 * @StatusBarKAnnor(statusBarType = StatusBarKType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
 * class StatusBarKActivity : BaseActivity<ActivityStatusbarkBinding, BaseViewModel>() {
 *  override fun initFlag() {
 *      StatusBarK.initStatusBar(this)
 *  }}
 */
object SenseKSystemBar {

    @JvmStatic
    fun init(
        activity: Activity,
        isImmersed: Boolean = false,
        isImmersedHard: Boolean = false,
        isImmersedSticky: Boolean = false,
        //////////////////////////////////////
        isStatusBarHide: Boolean = false,
        isNavigationBarHide: Boolean = false,
        isTitleBarHide: Boolean = false,
        isActionBarHide: Boolean = false,
        //////////////////////////////////////
        isStatusBarOverlay: Boolean = false,
        isNavigationBarOverlay: Boolean = false,
        isLayoutStable: Boolean = false,//设置布局不受系统栏出现隐藏的而改变位置
        isFitsSystemWindows: Boolean = false,//设置系统栏在控件上方的时候,不遮挡控件
        //////////////////////////////////////
        isStatusBarIconLowProfile: Boolean = false,
        isThemeCustom: Boolean = false,
        isThemeDark: Boolean = false
    ) {
        if (isStatusBarIconLowProfile) {
            SenseKSystemBarHelper.setSystemBarLowProfile(activity)
        }

        if (isThemeCustom) {
            SenseKSystemBarHelper.setSystemBarTheme(activity, isThemeDark)
        } else {
            SenseKSystemBarHelper.setSystemBarTheme(activity, UtilKConfiguration.isDarkMode())
        }

        if (isImmersed) {
            SenseKSystemBarHelper.hideSystemBar(activity, isStatusBarHide, isNavigationBarHide, isTitleBarHide, isActionBarHide)
            SenseKSystemBarHelper.overlaySystemBar(activity, isStatusBarOverlay, isNavigationBarOverlay)
            if (isLayoutStable) SenseKSystemBarHelper.setLayoutStable(activity)
            if (isFitsSystemWindows) SenseKSystemBarHelper.setFitsSystemWindows(activity)
            SenseKSystemBarHelper.setImmersed(activity, isImmersedHard, isImmersedSticky)
        }
    }

    @JvmStatic
    fun init(activity: Activity) {
        val statusBarAnnor: ASenseKSystemBar =
            activity.javaClass.getAnnotation(ASenseKSystemBar::class.java) ?: ASenseKSystemBar(systemBarType = CSystemBarType.NORMAL)
        val systemBarType = statusBarAnnor.systemBarType
        val isFollowSystem = statusBarAnnor.isFollowSystem
        val isFontIconDark = statusBarAnnor.isFontIconDark
        val bgColorLight = statusBarAnnor.bgColorLight
        val bgColorDark = statusBarAnnor.bgColorDark

        when (statusBarAnnor.systemBarType) {
            CSystemBarType.NORMAL -> {

            }

            CSystemBarType.LOW_PROFILE -> {
            }

            CSystemBarType.IMMERSED_LIGHT -> {
            }

            CSystemBarType.IMMERSED_FORCE -> {
            }

            CSystemBarType.IMMERSED_STICKY -> {
            }

            CSystemBarType.EXPAND_STATUS_BAR -> {

            }

            CSystemBarType.EXPAND_NAVIGATION_BAR -> {

            }

            CSystemBarType.EXPAND_ALL -> {

            }

//            ASenseKSystemBarType.FULL_SCREEN -> UtilKSystemBar.setImmersed(activity)//设置状态栏全屏
//            ASenseKSystemBarType.IMMERSED -> {
//                UtilKStatusBar.setImmersed(activity)//设置状态栏沉浸式
//                UtilKStatusBarFontIcon.setStatusBarFontIcon(activity, if (statusBarAnnor.isFollowSystem) UtilKUiMode.isOSLightMode() else statusBarAnnor.isFontIconDark)//设置状态栏文字和Icon是否为深色
//            }
//
//            else -> {
//                val statusBarColor = if (statusBarAnnor.isFollowSystem) {
//                    if (UtilKUiMode.isOSLightMode()) statusBarAnnor.bgColorLight else statusBarAnnor.bgColorDark
//                } else {
//                    if (statusBarAnnor.isFontIconDark) statusBarAnnor.bgColorLight else statusBarAnnor.bgColorDark
//                }
//                UtilKStatusBar.setStatusBarColor(activity, UtilKRes.getColor(statusBarColor))
//                UtilKStatusBarFontIcon.setStatusBarFontIcon(activity, if (statusBarAnnor.isFollowSystem) UtilKUiMode.isOSLightMode() else statusBarAnnor.isFontIconDark)//设置状态栏文字和Icon是否为深色
//            }
        }
    }
}