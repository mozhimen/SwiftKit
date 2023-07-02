package com.mozhimen.basick.sensek.systembar.cons

/**
 * @ClassName CSystemBarProperty
 * @Description
 *
 * 完整byte 0b0000_0000_0000_0000
 * 每位所代表的含义:
 * 0b
 * ////////////////////
 * 1 缺省
 * 0 缺省
 *
 * 0 系统栏是否沉浸式
 * 0不是
 * 1是
 *
 * 0 系统栏是否是硬沉浸
 * 0不是(点击任意位置显示)
 * 1是
 *
 * 0 系统栏是否粘性
 * 0非粘性
 * 1粘性
 * ////////////////////
 * 0 是否隐藏状态栏
 * 0不隐藏
 * 1隐藏
 *
 * 0 是否隐藏导航栏
 * 0不隐藏
 * 1隐藏
 *
 * 0 是否隐藏标题栏
 * 0不隐藏
 * 1隐藏
 *
 * 0 是否隐藏活动栏
 * 0不隐藏
 * 1隐藏
 * ////////////////////
 * 0 状态栏是否置于页面上方
 * 0不置于
 * 1置于
 *
 * 0 导航栏是否置于页面上方
 * 0不置于
 * 1置于
 *
 * 0 布局不受系统栏影响
 * 0受影响
 * 1不受影响
 *
 * 0 系统栏不遮挡控件
 * 0遮挡
 * 1不遮挡
 * ////////////////////
 *
 * 0 状态栏的字体是否为低调模式
 * 0不低调
 * 1低调
 *
 * 0 主题是否自定义
 * 0跟随系统
 * 1自定义
 *
 * 0 主题是否为深色
 * 0否,浅色
 * 1是,深色
 * ////////////////////
 *
 * 0b | x       0        0        0        |  0        0         0        0        | 0         0         0         0        | x       0         0         0        |
 * 0b | x     不沉浸    软沉浸    不粘性     | 显示状态栏 显示导航栏 显示标题栏 显示活动栏 | 状态栏不悬浮 导航栏不悬浮 stable关  fit关  | x     正常字体   跟随主题     浅色      |
 *
 * 0b | x       1        1        1       | 1         1         1        1        | 1         1          1         1        | x       1         1         1        |
 * 0b | x      沉浸    硬沉浸     粘性      | 隐藏状态栏 隐藏导航栏 隐藏标题栏 隐藏活动栏 | 状态栏悬浮 导航栏悬浮 stable开   fit开     | x       小字体     自定义主题 深色      |
 *
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 19:09
 * @Version 1.0
 */

/**
 * object CSystemBarType {
 *     const val NORMAL = 0//一般模式
 *     const val LOW_PROFILE = 1//低调模式, 状态栏图标变小
 *     const val IMMERSED_LIGHT = 2//沉浸模式, 界面全屏, 点击任意位置显示系统栏
 *     const val IMMERSED_FORCE = 3//沉浸模式, 界面全屏, 上下拉系统栏才会显示
 *     const val IMMERSED_STICKY = 4//沉浸模式, 界面全屏, 上下拉系统栏显示, 并消失
 *     const val IMMERSED_EXPAND_LIGHT = 5//沉浸扩展模式, 界面全屏, 界面拓展到拓展到系统栏的底部(状态栏,导航栏), 点击任意位置显示系统栏
 *     const val IMMERSED_EXPAND_FORCE = 6//沉浸扩展模式, 界面全屏, 界面拓展到拓展到系统栏的底部(状态栏,导航栏), 上下拉系统栏才会显示
 *     const val IMMERSED_EXPAND_STICKY = 7//沉浸扩展模式, 界面全屏, 界面拓展到拓展到系统栏的底部(状态栏,导航栏), 上下拉系统栏显示, 并消失
 *     const val EXPAND_STATUS_BAR = 8//拓展模式, 界面拓展到状态栏的底部
 *     const val EXPAND_NAVIGATION_BAR = 9//拓展模式, 界面拓展到导航栏的底部
 *     const val EXPAND_ALL = 10//拓展模式, 界面全屏, 并拓展到系统栏的底部(状态栏,导航栏)
 *     const val COLORFUL = 11//颜色模式, 设置颜色状态栏
 * }
 */
object CProperty {
    const val NORMAL = PreProperty.NORMAL//0b0000_0000_0000_0000
    const val IMMERSED_SOFT = PreProperty.IMMERSED_SOFT or PreProperty.HIDE_ALL or PreProperty.OVERLAY_ALL or PreProperty.LAYOUT_DEFAULT//0b0100_1111_1110_0000
    const val IMMERSED_HARD = PreProperty.IMMERSED_HARD or PreProperty.HIDE_ALL or PreProperty.OVERLAY_ALL or PreProperty.LAYOUT_DEFAULT//0b0110_1111_1110_0000
    const val IMMERSED_HARD_STICKY = PreProperty.IMMERSED_HARD_STICKY or PreProperty.HIDE_ALL or PreProperty.OVERLAY_ALL or PreProperty.LAYOUT_DEFAULT//0b0111_1111_1110_0000

    internal object PreProperty {
        const val NORMAL = Or.NORMAL//0b0000_0000_0000_0000
        const val IMMERSED_SOFT = Or.IMMERSED_OPEN//0b0100_0000_0000_0000
        const val IMMERSED_HARD = Or.IMMERSED_OPEN or Or.IMMERSED_HARD//0b0110_0000_0000_0000
        const val IMMERSED_HARD_STICKY = Or.IMMERSED_OPEN or Or.IMMERSED_HARD or Or.IMMERSED_STICKY//0b0111_0000_0000_0000
        const val HIDE_ALL = Or.HIDE_STATUS_BAR or Or.HIDE_NAVIGATION_BAR or Or.HIDE_TITLE_BAR or Or.HIDE_ACTION_BAR//0b0000_1111_0000_0000
        const val OVERLAY_ALL = Or.OVERLAY_STATUS_BAR or Or.OVERLAY_NAVIGATION_BAR//0b0000_0000_1100_0000
        const val LAYOUT_DEFAULT = Or.LAYOUT_STABLE//0b0000_0000_0010_0000
    }

    object Or {
        private const val OPEN = 0b1

        const val NORMAL = OPEN
        const val IMMERSED_OPEN = OPEN shl 14//0b0100_0000_0000_0000
        const val IMMERSED_HARD = OPEN shl 13//0b0010_0000_0000_0000
        const val IMMERSED_STICKY = OPEN shl 12//0b0001_0000_0000_0000

        const val HIDE_STATUS_BAR = OPEN shl 11//0b0000_1000_0000_0000
        const val HIDE_NAVIGATION_BAR = OPEN shl 10//0b0000_0100_0000_0000
        const val HIDE_TITLE_BAR = OPEN shl 9//0b0000_0010_0000_0000
        const val HIDE_ACTION_BAR = OPEN shl 8//0b0000_0001_0000_0000

        const val OVERLAY_STATUS_BAR = OPEN shl 7//0b0000_0000_1000_0000
        const val OVERLAY_NAVIGATION_BAR = OPEN shl 6//0b0000_0000_0100_0000
        const val LAYOUT_STABLE = OPEN shl 5//0b0000_0000_0010_0000
        const val FITS_SYSTEM_WINDOWS = OPEN shl 4//0b0000_0000_0001_0000

        const val STATUS_BAR_ICON_LOW_PROFILE = OPEN shl 2//0b0000_0000_0000_0100
        const val THEME_CUSTOM = OPEN shl 1//0b0000_0000_0000_0010
        const val THEME_DARK = OPEN//0b0000_0000_0000_0001
    }

    object And {
        const val IMMERSED_CLOSE = Or.IMMERSED_OPEN.inv()//0b0111_1111_1111_1111
        const val IMMERSED_SOFT = Or.IMMERSED_HARD.inv()//0b1101_1111_1111_1111
        const val IMMERSED_NO_STICKY = Or.IMMERSED_STICKY.inv()//0b1110_1111_1111_1111

        const val SHOW_STATUS_BAR = Or.HIDE_STATUS_BAR.inv()//0b1111_1111_0111_1111
        const val SHOW_NAVIGATION_BAR = Or.HIDE_NAVIGATION_BAR.inv()//0b1111_1011_1111_1111
        const val SHOW_TITLE_BAR = Or.HIDE_TITLE_BAR.inv()//0b1111_1111_1101_1111
        const val SHOW_ACTION_BAR = Or.HIDE_ACTION_BAR.inv()//0b1111_1111_1110_1111

        const val NO_OVERLAY_STATUS_BAR = Or.OVERLAY_STATUS_BAR.inv()//0b1111_1111_0111_1111
        const val NO_OVERLAY_NAVIGATION_BAR = Or.OVERLAY_NAVIGATION_BAR.inv()//0b1111_1111_1011_1111
        const val CLOSE_LAYOUT_STABLE = Or.LAYOUT_STABLE.inv()//0b1111_1111_1111_1101
        const val CLOSE_FITS_SYSTEM_WINDOWS = Or.FITS_SYSTEM_WINDOWS.inv()//0b1111_1111_1110_1111

        const val STATUS_BAR_ICON_NORMAL = Or.STATUS_BAR_ICON_LOW_PROFILE.inv()//0b1111_1111_1111_1011
        const val THEME_CUSTOM = Or.THEME_CUSTOM.inv()//0b1111_1111_1111_1101
        const val THEME_DARK = Or.THEME_DARK.inv()//0b1111_1111_1111_1110
    }
}
