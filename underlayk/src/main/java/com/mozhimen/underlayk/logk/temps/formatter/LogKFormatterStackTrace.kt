package com.mozhimen.underlayk.logk.temps.formatter

import com.mozhimen.underlayk.logk.commons.ILogKFormatter
import java.lang.StringBuilder

/**
 * @ClassName StackTraceFormatter
 * @Description 堆栈信息格式化
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:37
 * @Version 1.0
 */
class LogKFormatterStackTrace: ILogKFormatter<Array<StackTraceElement?>> {
    override fun format(stackTrace: Array<StackTraceElement?>): String? {
        val builder = StringBuilder(128)
        return when {
            stackTrace.isEmpty() -> {
                null
            }
            stackTrace.size == 1 -> {
                "- " + stackTrace[0].toString()
            }
            else -> {
                var i = 0
                val len: Int = stackTrace.size
                while (i < len) {
                    if (i == 0) {
                        builder.append("StackTrace:\n")
                    }
                    if (i != len - 1) {
                        builder.append("├ ")
                        builder.append(stackTrace[i].toString())
                        builder.append("\n")
                    } else {
                        builder.append("└ ")
                        builder.append(stackTrace[i].toString())
                    }
                    i++
                }
                builder.toString()
            }
        }
    }
}