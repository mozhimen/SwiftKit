package com.mozhimen.uicorek.adaptk.systembar.annors

import androidx.annotation.IntDef
import com.mozhimen.uicorek.adaptk.systembar.cons.CProperty

/**
 * @ClassName StatusBarKType
 * @Description 后期还会加, 目前先预制这么多
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:41
 * @Version 1.0
 */
@IntDef(value = [CProperty.NORMAL, CProperty.IMMERSED_SOFT, CProperty.IMMERSED_HARD, CProperty.IMMERSED_HARD_STICKY])
annotation class APropertyFilter