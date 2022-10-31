package com.mozhimen.underlayk.logk.commons

import com.mozhimen.basick.extsk.toJson
import com.mozhimen.underlayk.logk.temps.LogKFormatterStackTrace
import com.mozhimen.underlayk.logk.temps.LogKFormatterThread

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
        var formatterThread: LogKFormatterThread = LogKFormatterThread()
        var formatterStackTrace: LogKFormatterStackTrace = LogKFormatterStackTrace()
    }

    open fun injectJsonParser(): IJsonParser? {
        return object : IJsonParser {
            override fun toJson(src: Any): String {
                return src.toJson()
            }
        }
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
        return 0
    }

    open fun printers(): Array<ILogKPrinter>? {
        return null
    }

    interface IJsonParser {
        fun toJson(src: Any): String
    }
}