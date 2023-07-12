package com.mozhimen.uicorek.adaptk.systembar.annors

import com.mozhimen.uicorek.adaptk.systembar.cons.CProperty

/**
 * @ClassName StatusBarAnnor
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
/**
 * @Target(AnnotationTarget.CLASS)
 * @Retention(AnnotationRetention.RUNTIME)
 * annotation class ASenseKSystemBar(
 *     @ASenseKSystemBarType
 *     val systemBarType: Int = CSystemBarType.NORMAL,
 *     val isFollowSystem: Boolean = false, //状态栏主题是否跟随系统
 *     val isFontIconDark: Boolean = true,//状态栏文字和Icon是否是深色(黑/白)
 *     val bgColorLight: Int = android.R.color.white, //状态栏背景色(浅色主题)
 *     val bgColorDark: Int = android.R.color.black //状态栏背景色(深色主题)
 * )
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AAdaptKSystemBarProperty(
    @APropertyFilter
    val property: Int = CProperty.NORMAL,
)
