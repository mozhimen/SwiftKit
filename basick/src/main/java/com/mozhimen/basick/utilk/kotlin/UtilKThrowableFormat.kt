package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @ClassName UtilKThrowable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 0:19
 * @Version 1.0
 */
fun Throwable.throwable2printWriter(printWriter: PrintWriter) {
    UtilKThrowable.throwable2printWriter(this, printWriter)
}

fun Throwable.throwable2str(): String =
    UtilKThrowable.throwable2str(this)

object UtilKThrowable : IUtilK {
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

    @JvmStatic
    fun throwable2str(throwable: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        try {
            throwable.printStackTrace(printWriter)
            var cause = throwable.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            return stringWriter.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            printWriter.flush()
            printWriter.close()
            stringWriter.flush()
            stringWriter.close()
        }
        return ""
    }
}