package com.mozhimen.basick.sensek.systembar.annors

/**
 * @ClassName ASenseKSystemBarColor
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ASenseKSystemBarTheme(
    val colorLight: Int = android.R.color.white, //状态栏背景色(浅色主题)
    val colorDark: Int = android.R.color.black //状态栏背景色(深色主题)
)
