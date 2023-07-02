package com.mozhimen.basick.sensek.systembar

import android.app.Activity
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBarProperty
import com.mozhimen.basick.sensek.systembar.cons.CProperty
import com.mozhimen.basick.sensek.systembar.helpers.SenseKSystemBarHelper
import com.mozhimen.basick.sensek.systembar.mos.MPropertyConfig
import com.mozhimen.basick.utilk.android.app.getAnnotation
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.basick.utilk.kotlin.getByteStr
import com.mozhimen.basick.utilk.kotlin.toBoolean

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
fun Activity.initSenseKSystemBar() {
    SenseKSystemBar.initAnnor(this)
}

object SenseKSystemBar {

    @JvmStatic
    internal fun initAnnor(activity: Activity) {
        val systemBarAnnor: ASenseKSystemBarProperty = activity.getAnnotation(ASenseKSystemBarProperty::class.java) ?: ASenseKSystemBarProperty(CProperty.NORMAL)
        val propertyInt = systemBarAnnor.property
        init(activity, getConfigForByteInt(propertyInt))
    }

    @JvmStatic
    fun init(activity: Activity, mPropertyConfig: MPropertyConfig) {
        init(
            activity,
            mPropertyConfig.isImmersed,
            mPropertyConfig.isImmersedHard,
            mPropertyConfig.isImmersedSticky,
            mPropertyConfig.isHideStatusBar,
            mPropertyConfig.isHideNavigationBar,
            mPropertyConfig.isHideTitleBar,
            mPropertyConfig.isHideActionBar,
            mPropertyConfig.isOverlayStatusBar,
            mPropertyConfig.isOverlayNavigationBar,
            mPropertyConfig.isLayoutStable,
            mPropertyConfig.isFitsSystemWindows,
            mPropertyConfig.isStatusBarIconLowProfile,
            mPropertyConfig.isThemeCustom,
            mPropertyConfig.isThemeDark
        )
    }

    @JvmStatic
    fun init(
        activity: Activity,
        isImmersed: Boolean = false,
        isImmersedHard: Boolean = false,
        isImmersedSticky: Boolean = false,
        //////////////////////////////////////
        isHideStatusBar: Boolean = false,
        isHideNavigationBar: Boolean = false,
        isHideTitleBar: Boolean = false,
        isHideActionBar: Boolean = false,
        //////////////////////////////////////
        isOverlayStatusBar: Boolean = false,
        isOverlayNavigationBar: Boolean = false,
        isLayoutStable: Boolean = false,//设置布局不受系统栏出现隐藏的而改变位置
        isFitsSystemWindows: Boolean = false,//设置系统栏在控件上方的时候,不遮挡控件
        //////////////////////////////////////
        isStatusBarIconLowProfile: Boolean = false,
        isThemeCustom: Boolean = false,
        isThemeDark: Boolean = false
    ) {
        if (isStatusBarIconLowProfile) SenseKSystemBarHelper.setSystemBarLowProfile(activity)
        if (isThemeCustom) SenseKSystemBarHelper.setSystemBarTheme(activity, isThemeDark) else SenseKSystemBarHelper.setSystemBarTheme(activity, UtilKConfiguration.isDarkMode())
        if (isImmersed) {
            SenseKSystemBarHelper.hideSystemBar(activity, isHideStatusBar, isHideNavigationBar, isHideTitleBar, isHideActionBar)
            SenseKSystemBarHelper.overlaySystemBar(activity, isOverlayStatusBar, isOverlayNavigationBar)
            if (isLayoutStable) SenseKSystemBarHelper.setLayoutStable(activity)
            if (isFitsSystemWindows) SenseKSystemBarHelper.setFitsSystemWindows(activity)
            SenseKSystemBarHelper.setImmersed(activity, isImmersedHard, isImmersedSticky)
        }
    }

    private fun getConfigForByteInt(byteInt: Int): MPropertyConfig {
        val mPropertyConfig = MPropertyConfig()
        val byteStr = byteInt.getByteStr(16)
        var byteBoolean = false
        byteStr.forEachIndexed { position, c ->
            byteBoolean = c.digitToInt().toBoolean()
            when (position) {
                1-> mPropertyConfig.isImmersed = byteBoolean
                2-> mPropertyConfig.isImmersedHard = byteBoolean
                3-> mPropertyConfig.isImmersedSticky = byteBoolean
                ////////////////////////////////////////////
                4-> mPropertyConfig.isHideStatusBar = byteBoolean
                5-> mPropertyConfig.isHideNavigationBar = byteBoolean
                6-> mPropertyConfig.isHideTitleBar = byteBoolean
                7-> mPropertyConfig.isHideActionBar = byteBoolean
                ////////////////////////////////////////////
                8-> mPropertyConfig.isOverlayStatusBar = byteBoolean
                9-> mPropertyConfig.isOverlayNavigationBar = byteBoolean
                10-> mPropertyConfig.isLayoutStable = byteBoolean
                11-> mPropertyConfig.isFitsSystemWindows = byteBoolean
                ////////////////////////////////////////////
                13-> mPropertyConfig.isStatusBarIconLowProfile = byteBoolean
                14-> mPropertyConfig.isThemeCustom = byteBoolean
                15-> mPropertyConfig.isThemeDark = byteBoolean
            }
        }
        return mPropertyConfig
    }


    @JvmStatic
    internal fun init(activity: Activity) {
//        val statusBarAnnor: ASenseKSystemBarProperty =
//            activity.getAnnotation(ASenseKSystemBarProperty::class.java) ?: ASenseKSystemBarProperty(property = CSystemBarType.NORMAL)
//        val systemBarType = statusBarAnnor.property
//        val isFollowSystem = statusBarAnnor.isFollowSystem
//        val isFontIconDark = statusBarAnnor.isFontIconDark
//        val bgColorLight = statusBarAnnor.bgColorLight
//        val bgColorDark = statusBarAnnor.bgColorDark
//
//        when (statusBarAnnor.property) {
//            CSystemBarType.NORMAL -> {
//
//            }
//
//            CSystemBarType.LOW_PROFILE -> {
//            }
//
//            CSystemBarType.IMMERSED_LIGHT -> {
//            }
//
//            CSystemBarType.IMMERSED_FORCE -> {
//            }
//
//            CSystemBarType.IMMERSED_STICKY -> {
//            }
//
//            CSystemBarType.EXPAND_STATUS_BAR -> {
//
//            }
//
//            CSystemBarType.EXPAND_NAVIGATION_BAR -> {
//
//            }
//
//            CSystemBarType.EXPAND_ALL -> {
//
//            }

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