package com.mozhimen.basicsk.logk.mos

import com.mozhimen.basicsk.extsk.toJson
import com.mozhimen.basicsk.logk.commons.IPrinter
import com.mozhimen.basicsk.logk.helpers.FormatterStackTrace
import com.mozhimen.basicsk.logk.helpers.FormatterThread

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
        var formatterThread: FormatterThread = FormatterThread()
        var formatterStackTrace: FormatterStackTrace = FormatterStackTrace()
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

    open fun printers(): Array<IPrinter>? {
        return null
    }

    interface IJsonParser {
        fun toJson(src: Any): String
    }
}