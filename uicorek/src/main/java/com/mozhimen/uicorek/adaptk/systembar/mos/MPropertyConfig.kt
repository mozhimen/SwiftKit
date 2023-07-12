package com.mozhimen.uicorek.adaptk.systembar.mos

/**
 * @ClassName MPropertyConfig
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/2 23:38
 * @Version 1.0
 */
data class MPropertyConfig(
    var isImmersed: Boolean = false,
    var isImmersedHard: Boolean = false,
    var isImmersedSticky: Boolean = false,
    //////////////////////////////////////
    var isHideStatusBar: Boolean = false,
    var isHideNavigationBar: Boolean = false,
    var isHideTitleBar: Boolean = false,
    var isHideActionBar: Boolean = false,
    //////////////////////////////////////
    var isOverlayStatusBar: Boolean = false,
    var isOverlayNavigationBar: Boolean = false,
    var isLayoutStable: Boolean = false,//设置布局不受系统栏出现隐藏的而改变位置
    var isFitsSystemWindows: Boolean = false,//设置系统栏在控件上方的时候,不遮挡控件
    //////////////////////////////////////
    var isStatusBarBgTranslucent:Boolean = false,
    var isStatusBarIconLowProfile: Boolean = false,
    var isThemeCustom: Boolean = false,
    var isThemeDark: Boolean = false
)