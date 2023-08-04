package com.mozhimen.basick.utilk.java.io

import java.io.OutputStream

/**
 * @ClassName UtilKOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/4 13:49
 * @Version 1.0
 */
fun OutputStream.flushClose() {
    UtilKOutputStream.flushClose(this)
}

object UtilKOutputStream {
    @JvmStatic
    fun flushClose(outputStream: OutputStream) {
        outputStream.apply {
            flush()
            close()
        }
    }
}