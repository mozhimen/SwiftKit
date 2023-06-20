package com.mozhimen.underlayk.logk.bases

import com.mozhimen.basick.utilk.java.datatype.json.moshiT2Json
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterStackTrace
import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterThread

/**
 * @ClassName LogKConfig
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:04
 * @Version 1.0
 */
open class BaseLogKConfig {
    open fun injectJsonParser(): IJsonParser? {
        return object : IJsonParser {
            override fun toJson(src: Any): String {
                return src.moshiT2Json()
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