package com.mozhimen.basick.sensek.systembar.cons

/**
 * @ClassName CPropertyOr
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/3 15:00
 * @Version 1.0
 */
object CPropertyOr {
    private const val OPEN = 0b1
    private const val CLOSE = 0b0

    const val NORMAL = CLOSE
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

    const val STATUS_BAR_BG_TRANSLUCENT = OPEN shl 3//0b0000_0000_0000_1000
    const val STATUS_BAR_ICON_LOW_PROFILE = OPEN shl 2//0b0000_0000_0000_0100
    const val THEME_CUSTOM = OPEN shl 1//0b0000_0000_0000_0010
    const val THEME_DARK = OPEN//0b0000_0000_0000_0001
}