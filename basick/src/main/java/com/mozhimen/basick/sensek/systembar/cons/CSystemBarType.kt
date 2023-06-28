package com.mozhimen.basick.sensek.systembar.cons

/**
 * @ClassName CStackBarKType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/28 11:35
 * @Version 1.0
 */
object CSystemBarType {
    const val NORMAL = -2//一般模式
    const val LOW_PROFILE = -1//低调模式, 状态栏图标变小
    const val IMMERSED_LIGHT = 0//沉浸模式, 界面全屏, 点击任意位置显示系统栏
    const val IMMERSED_FORCE = 1//沉浸模式, 界面全屏, 上下拉系统栏才会显示
    const val IMMERSED_STICKY = 2//沉浸模式, 界面全屏, 上下拉系统栏显示, 并消失
    const val EXPAND_STATUS_BAR = 3//拓展模式, 界面拓展到状态栏的底部
    const val EXPAND_NAVIGATION_BAR = 4//拓展模式, 界面拓展到导航栏的底部
    const val EXPAND_ALL = 5//拓展模式, 界面全屏, 并拓展到系统栏的底部(状态栏,导航栏)
    const val COLORFUL = 6//颜色模式, 设置颜色状态栏
}