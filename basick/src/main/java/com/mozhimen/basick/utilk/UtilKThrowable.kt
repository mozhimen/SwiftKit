package com.mozhimen.basick.utilk

import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer

/**
 * @ClassName UtilKThrowable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 0:19
 * @Version 1.0
 */
object UtilKThrowable {
    @JvmStatic
    fun throwable2String(throwable: Throwable): String {
        val stringWriter: Writer = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        throwable.printStackTrace(printWriter)
        var cause = throwable.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        val crashString = stringWriter.toString()
        printWriter.close()
        return crashString
    }
}