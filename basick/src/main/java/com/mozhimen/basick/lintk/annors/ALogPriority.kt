package com.mozhimen.basick.lintk.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority

/**
 * @ClassName LogKType
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:59
 * @Version 1.0
 */
@IntDef(value = [CLogPriority.V, CLogPriority.D, CLogPriority.I, CLogPriority.W, CLogPriority.E, CLogPriority.A])
@Retention(AnnotationRetention.SOURCE)
annotation class ALogPriority
