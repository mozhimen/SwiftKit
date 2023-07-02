package com.mozhimen.basick.sensek.systembar.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.sensek.systembar.cons.CProperty

/**
 * @ClassName StatusBarKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:41
 * @Version 1.0
 */
@IntDef(
    value = [
        CProperty.NORMAL,
        CProperty.IMMERSED_SOFT,
        CProperty.IMMERSED_HARD,
        CProperty.IMMERSED_HARD_STICKY,
    ]
)
annotation class APropertyFilter