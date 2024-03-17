package com.mozhimen.basick.elemk.android.util.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.elemk.android.util.cons.CLog

/**
 * @ClassName ALogPriority
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:59
 * @Version 1.0
 */
@IntDef(value = [CLog.VERBOSE, CLog.DEBUG, CLog.INFO, CLog.WARN, CLog.ERROR, CLog.ASSERT])
@Retention(AnnotationRetention.SOURCE)
annotation class ALog
