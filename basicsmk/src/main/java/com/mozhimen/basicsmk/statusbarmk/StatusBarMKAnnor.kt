package com.mozhimen.basicsmk.statusbarmk

/**
 * @ClassName StatusBarAnnor
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class StatusBarMKAnnor(
    val isImmersed: Boolean = true,//是否沉浸式状态栏
    val statusBarColor: Int = android.R.color.white,//状态栏背景色
    val isTextAndIconDark: Boolean = true//状态栏文字和Icon是否是深色(黑/白)
)
