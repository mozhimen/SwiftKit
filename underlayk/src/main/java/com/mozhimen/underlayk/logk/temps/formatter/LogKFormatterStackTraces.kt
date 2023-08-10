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
class LogKFormatterStackTraces: ILogKFormatter<Array<StackTraceElement?>> {
    override fun format(data: Array<StackTraceElement?>): String? {
        val stringBuilder = StringBuilder(128)
        return when {
            data.isEmpty() -> null
            data.size == 1 -> "- " + data[0].toString()
            else -> {
                var i = 0
                val len: Int = data.size
                while (i < len) {
                    if (i == 0) {
                        stringBuilder.append("StackTrace:\n")
                    }
                    if (i != len - 1) {
                        stringBuilder.append("├ ")
                        stringBuilder.append(data[i].toString())
                        stringBuilder.append("\n")
                    } else {
                        stringBuilder.append("└ ")
                        stringBuilder.append(data[i].toString())
                    }
                    i++
                }
                stringBuilder.toString()
            }
        }
    }
}