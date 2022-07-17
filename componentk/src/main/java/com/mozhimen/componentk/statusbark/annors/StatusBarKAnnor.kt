package com.mozhimen.componentk.statusbark.annors

/**
 * @ClassName StatusBarAnnor
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class StatusBarKAnnor(
    @StatusBarKType
    val statusBarType: Int = StatusBarKType.FULL_SCREEN,
    val isFontIconDark: Boolean = true,//状态栏文字和Icon是否是深色(黑/白)
    val isFollowSystem: Boolean = false, //状态栏主题是否跟随系统
    val bgColorLight: Int = android.R.color.white, //状态栏背景色(浅色主题)
    val bgColorDark: Int = android.R.color.black //状态栏背景色(深色主题)
)
