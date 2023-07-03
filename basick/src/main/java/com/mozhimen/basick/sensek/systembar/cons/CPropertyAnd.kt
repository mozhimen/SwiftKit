package com.mozhimen.basick.sensek.systembar.cons

/**
 * @ClassName CPropertyAnd
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/3 15:05
 * @Version 1.0
 */
object CPropertyAnd {
    const val NORMAL = CPropertyOr.NORMAL.inv()
    const val IMMERSED_CLOSE = CPropertyOr.IMMERSED_OPEN.inv()//0b0111_1111_1111_1111
    const val IMMERSED_SOFT = CPropertyOr.IMMERSED_HARD.inv()//0b1101_1111_1111_1111
    const val IMMERSED_NO_STICKY = CPropertyOr.IMMERSED_STICKY.inv()//0b1110_1111_1111_1111

    const val SHOW_STATUS_BAR = CPropertyOr.HIDE_STATUS_BAR.inv()//0b1111_1111_0111_1111
    const val SHOW_NAVIGATION_BAR = CPropertyOr.HIDE_NAVIGATION_BAR.inv()//0b1111_1011_1111_1111
    const val SHOW_TITLE_BAR = CPropertyOr.HIDE_TITLE_BAR.inv()//0b1111_1111_1101_1111
    const val SHOW_ACTION_BAR = CPropertyOr.HIDE_ACTION_BAR.inv()//0b1111_1111_1110_1111

    const val NO_OVERLAY_STATUS_BAR = CPropertyOr.OVERLAY_STATUS_BAR.inv()//0b1111_1111_0111_1111
    const val NO_OVERLAY_NAVIGATION_BAR = CPropertyOr.OVERLAY_NAVIGATION_BAR.inv()//0b1111_1111_1011_1111
    const val CLOSE_LAYOUT_STABLE = CPropertyOr.LAYOUT_STABLE.inv()//0b1111_1111_1111_1101
    const val CLOSE_FITS_SYSTEM_WINDOWS = CPropertyOr.FITS_SYSTEM_WINDOWS.inv()//0b1111_1111_1110_1111

    const val STATUS_BAR_BG_COLORFUL = CPropertyOr.STATUS_BAR_BG_TRANSLUCENT.inv()//0b1111_1111_1111_0111
    const val STATUS_BAR_ICON_NORMAL = CPropertyOr.STATUS_BAR_ICON_LOW_PROFILE.inv()//0b1111_1111_1111_1011
    const val THEME_FOLLOW_SYSTEM = CPropertyOr.THEME_CUSTOM.inv()//0b1111_1111_1111_1101
    const val THEME_LIGHT = CPropertyOr.THEME_DARK.inv()//0b1111_1111_1111_1110
}