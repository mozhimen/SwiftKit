package com.mozhimen.basick.utilk.java.util

import java.io.OutputStream
import java.util.zip.GZIPOutputStream

/**
 * @ClassName UtilKGZIPOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKGZIPOutputStream {
    @JvmStatic
    fun get(outputStream: OutputStream, size: Int? = null, syncFlush: Boolean? = null): GZIPOutputStream =
        if (size != null && size > 0 && syncFlush != null) {
            GZIPOutputStream(outputStream, size, syncFlush)
        } else if (size != null && size > 0) {
            GZIPOutputStream(outputStream, size)
        } else if (syncFlush != null) {
            GZIPOutputStream(outputStream, syncFlush)
        } else
            GZIPOutputStream(outputStream)
}