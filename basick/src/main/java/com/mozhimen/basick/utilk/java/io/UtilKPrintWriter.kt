package com.mozhimen.basick.utilk.java.io

import java.io.PrintWriter


/**
 * @ClassName UtilKPrintWriter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 10:44
 * @Version 1.0
 */
fun Throwable.throwable2printWriter(printWriter: PrintWriter) {
    UtilKPrintWriter.throwable2printWriter(this, printWriter)
}

object UtilKPrintWriter {
    @JvmStatic
    fun throwable2printWriter(e: Throwable, printWriter: PrintWriter) {
        printWriter.use {
            e.printStackTrace(it)
            var cause = e.cause
            while (cause != null) {
                cause.printStackTrace(it)
                cause = cause.cause
            }
        }
    }
}