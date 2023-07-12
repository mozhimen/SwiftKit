package com.mozhimen.uicorek.adaptk.systembar.annors

import androidx.annotation.IntDef
import com.mozhimen.uicorek.adaptk.systembar.cons.CPropertyAnd

/**
 * @ClassName StatusBarKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:41
 * @Version 1.0
 */
@IntDef(
    value = [
        CPropertyAnd.NORMAL,
        CPropertyAnd.IMMERSED_CLOSE,
        CPropertyAnd.IMMERSED_SOFT,
        CPropertyAnd.IMMERSED_NO_STICKY,
        CPropertyAnd.SHOW_STATUS_BAR,
        CPropertyAnd.SHOW_NAVIGATION_BAR,
        CPropertyAnd.SHOW_TITLE_BAR,
        CPropertyAnd.SHOW_ACTION_BAR,
        CPropertyAnd.NO_OVERLAY_STATUS_BAR,
        CPropertyAnd.NO_OVERLAY_NAVIGATION_BAR,
        CPropertyAnd.CLOSE_LAYOUT_STABLE,
        CPropertyAnd.CLOSE_FITS_SYSTEM_WINDOWS,
        CPropertyAnd.STATUS_BAR_BG_COLORFUL,
        CPropertyAnd.STATUS_BAR_ICON_NORMAL,
        CPropertyAnd.THEME_FOLLOW_SYSTEM,
        CPropertyAnd.THEME_LIGHT,
    ]
)
annotation class APropertyFilterAnd