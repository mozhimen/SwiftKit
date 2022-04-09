package com.mozhimen.basicsk.logk.helpers

import com.mozhimen.basicsk.logk.commons.ILogKFormatter
import java.lang.StringBuilder

/**
 * @ClassName StackTraceFormatter
 * @Description 堆栈信息格式化
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:37
 * @Version 1.0
 */
class StackTraceFormatter: ILogKFormatter<Array<StackTraceElement?>> {
    override fun format(stackTrace: Array<StackTraceElement?>): String? {
        val builder = StringBuilder(128)
        return when {
            stackTrace.isEmpty() -> {
                null
            }
            stackTrace.size == 1 -> {
                "\t- " + stackTrace[0].toString()
            }
            else -> {
                var i = 0
                val len: Int = stackTrace.size
                while (i < len) {
                    if (i == 0) {
                        builder.append("stackTrace: \n")
                    }
                    if (i != len - 1) {
                        builder.append("\t├ ")
                        builder.append(stackTrace[i].toString())
                        builder.append("\n")
                    } else {
                        builder.append("\t└ ")
                        builder.append(stackTrace[i].toString())
                    }
                    i++
                }
                builder.toString()
            }
        }
    }
}