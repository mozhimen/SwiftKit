package com.mozhimen.underlayk.logk.cons

import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterStackTrace
import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterThread

/**
 * @ClassName CLogKParameter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 19:36
 * @Version 1.0
 */
object CLogKCons {
    var FORMATTER_THREAD: LogKFormatterThread = LogKFormatterThread()
    var FORMATTER_STACK_TRACE: LogKFormatterStackTrace = LogKFormatterStackTrace()

    const val LOG_MAX_LEN = 512
    const val TAG_LOGK_CONTAINER_VIEW = "TAG_LOGK_CONTAINER_VIEW"
    const val TAG_LOGK_MONITOR_VIEW = "TAG_LOGK_MONITOR_VIEW"

    val TITLE_OPEN_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_open)
    val TITLE_CLOSE_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_close)
}