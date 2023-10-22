package com.mozhimen.uicorek.adaptk.systembar.cons

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
 * 状态栏的背景是否透明
 * 0不透明
 * 1透明
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
 * 0b | x       0        0        0        |  0        0         0        0        | 0         0         0         0        | 0       0         0         0        |
 * 0b | x     不沉浸    软沉浸    不粘性     | 显示状态栏 显示导航栏 显示标题栏 显示活动栏 | 状态栏不悬浮 导航栏不悬浮 stable关  fit关  | 不透明 正常字体   跟随主题     浅色      |
 *
 * 0b | x       1        1        1       | 1         1         1        1        | 1         1          1         1        | 1       1         1         1        |
 * 0b | x      沉浸    硬沉浸     粘性      | 隐藏状态栏 隐藏导航栏 隐藏标题栏 隐藏活动栏 | 状态栏悬浮 导航栏悬浮 stable开   fit开     | 透明    小字体     自定义主题 深色      |
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
    const val IMMERSED_SOFT = PreProperty.IMMERSED_SOFT or PreProperty.HIDE_ALL
    const val IMMERSED_HARD = PreProperty.IMMERSED_HARD or PreProperty.HIDE_ALL or PreProperty.OVERLAY_ALL or PreProperty.LAYOUT_DEFAULT
    const val IMMERSED_HARD_STICKY = PreProperty.IMMERSED_HARD_STICKY or PreProperty.HIDE_ALL or PreProperty.OVERLAY_ALL or PreProperty.LAYOUT_DEFAULT
    const val TRANSLUCENT = PreProperty.NORMAL or PreProperty.OVERLAY_ALL or CPropertyOr.STATUS_BAR_BG_TRANSLUCENT
    const val TRANSLUCENT_LIGHT = PreProperty.NORMAL or PreProperty.OVERLAY_ALL or CPropertyOr.STATUS_BAR_BG_TRANSLUCENT or CPropertyOr.THEME_CUSTOM and CPropertyAnd.THEME_LIGHT
    /*
     * 状态栏深色图标
     */
    const val TRANSLUCENT_DARK = PreProperty.NORMAL or PreProperty.OVERLAY_ALL or CPropertyOr.STATUS_BAR_BG_TRANSLUCENT or CPropertyOr.THEME_CUSTOM or CPropertyOr.THEME_DARK

    internal object PreProperty {
        const val NORMAL = CPropertyOr.NORMAL//0b0000_0000_0000_0000
        const val IMMERSED_SOFT = CPropertyOr.IMMERSED_OPEN//0b0100_0000_0000_0000
        const val IMMERSED_HARD = CPropertyOr.IMMERSED_OPEN or CPropertyOr.IMMERSED_HARD//0b0110_0000_0000_0000
        const val IMMERSED_HARD_STICKY = CPropertyOr.IMMERSED_OPEN or CPropertyOr.IMMERSED_HARD or CPropertyOr.IMMERSED_STICKY//0b0111_0000_0000_0000
        const val HIDE_ALL = CPropertyOr.HIDE_STATUS_BAR or CPropertyOr.HIDE_NAVIGATION_BAR or CPropertyOr.HIDE_TITLE_BAR or CPropertyOr.HIDE_ACTION_BAR//0b0000_1111_0000_0000
        const val OVERLAY_ALL = CPropertyOr.OVERLAY_STATUS_BAR or CPropertyOr.OVERLAY_NAVIGATION_BAR//0b0000_0000_1100_0000
        const val LAYOUT_DEFAULT = CPropertyOr.LAYOUT_STABLE or CPropertyOr.FITS_SYSTEM_WINDOWS
    }
}
