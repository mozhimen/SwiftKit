package com.mozhimen.basicsmk.logmk.mos

import com.mozhimen.basicsmk.logmk.commons.ILogMKPrinter
import com.mozhimen.basicsmk.logmk.helpers.StackTraceFormatter
import com.mozhimen.basicsmk.logmk.helpers.ThreadFormatter

/**
 * @ClassName LogMKConfig
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:04
 * @Version 1.0
 */
open class LogMKConfig {
    companion object {
        const val MAX_LEN = 512
        var THREAD_FORMATTER: ThreadFormatter = ThreadFormatter()
        var STACK_TRACE_FORMATTER: StackTraceFormatter = StackTraceFormatter()
    }

    open fun injectJsonParser(): JsonParser? {
        return null
    }

    open fun getGlobalTag(): String {
        return "LogMK"
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

    open fun printers(): Array<ILogMKPrinter>? {
        return null
    }

    interface JsonParser {
        fun toJson(src: Any): String
    }
}