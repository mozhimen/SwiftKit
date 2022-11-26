package com.mozhimen.underlayk.logk.mos

import androidx.annotation.IntDef
import com.mozhimen.basick.utilk.log.cons.CLogType

/**
 * @ClassName LogKType
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:59
 * @Version 1.0
 */
@IntDef(
    CLogType.V,
    CLogType.D,
    CLogType.I,
    CLogType.W,
    CLogType.E,
    CLogType.A
)
@Retention(AnnotationRetention.SOURCE)
annotation class ALogKType
