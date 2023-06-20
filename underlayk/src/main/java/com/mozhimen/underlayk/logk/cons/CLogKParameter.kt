package com.mozhimen.underlayk.logk.cons

import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterStackTrace
import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterThread

/**
 * @ClassName CLogKParameter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 19:36
 * @Version 1.0
 */
object CLogKParameter {
    var FORMATTER_THREAD: LogKFormatterThread = LogKFormatterThread()
    var FORMATTER_STACK_TRACE: LogKFormatterStackTrace = LogKFormatterStackTrace()
    const val LOG_MAX_LEN = 512
    const val TAG_LOGK_CONTAINER_VIEW = "TAG_LOGK_CONTAINER_VIEW"
    const val TAG_LOGK_MONITOR_VIEW = "TAG_LOGK_MONITOR_VIEW"
}