package com.mozhimen.basick.sensek.systembar.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.sensek.systembar.cons.CSystemBarType

/**
 * @ClassName StatusBarKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:41
 * @Version 1.0
 */
@IntDef(
    value = [
        CSystemBarType.NORMAL,
        CSystemBarType.LOW_PROFILE,
        CSystemBarType.IMMERSED_LIGHT,
        CSystemBarType.IMMERSED_FORCE,
        CSystemBarType.IMMERSED_STICKY,
        CSystemBarType.IMMERSED_EXPAND_LIGHT,
        CSystemBarType.IMMERSED_EXPAND_FORCE,
        CSystemBarType.IMMERSED_EXPAND_STICKY,
        CSystemBarType.EXPAND_STATUS_BAR,
        CSystemBarType.EXPAND_NAVIGATION_BAR,
        CSystemBarType.EXPAND_ALL,
        CSystemBarType.COLORFUL,
    ]
)
annotation class ASenseKSystemBarType