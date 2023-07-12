package com.mozhimen.underlayk.logk.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.elemk.cons.CLogPriority

/**
 * @ClassName LogKType
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:59
 * @Version 1.0
 */
@IntDef(
    CLogPriority.V,
    CLogPriority.D,
    CLogPriority.I,
    CLogPriority.W,
    CLogPriority.E,
    CLogPriority.A
)
@Retention(AnnotationRetention.SOURCE)
annotation class ALogKType
