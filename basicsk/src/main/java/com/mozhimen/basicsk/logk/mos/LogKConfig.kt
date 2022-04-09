package com.mozhimen.basicsk.logk.mos

import com.mozhimen.basicsk.logk.commons.ILogKPrinter
import com.mozhimen.basicsk.logk.helpers.StackTraceFormatter
import com.mozhimen.basicsk.logk.helpers.ThreadFormatter

/**
 * @ClassName LogKConfig
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:04
 * @Version 1.0
 */
open class LogKConfig {
    companion object {
        const val MAX_LEN = 512
        var THREAD_FORMATTER: ThreadFormatter = ThreadFormatter()
        var STACK_TRACE_FORMATTER: StackTraceFormatter = StackTraceFormatter()
    }

    open fun injectJsonParser(): JsonParser? {
        return null
    }

    open fun getGlobalTag(): String {
        return "LogK"
    }

    open fun enable(): Boolean {
        return true
    }

    open fun includeThread(): Boolean {
        return false
    }

    open fun stackTraceDepth(): Int {
        return 5
    }

    open fun printers(): Array<ILogKPrinter>? {
        return null
    }

    interface JsonParser {
        fun toJson(src: Any): String
    }
}