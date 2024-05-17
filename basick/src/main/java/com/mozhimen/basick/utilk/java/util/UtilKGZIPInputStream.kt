package com.mozhimen.basick.utilk.java.util

import java.io.InputStream
import java.util.zip.GZIPInputStream

/**
 * @ClassName UtilKGZIPInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKGZIPInputStream {
    @JvmStatic
    fun get(inputStream: InputStream, size: Int? = null): GZIPInputStream =
        if (size != null && size > 0)
            GZIPInputStream(inputStream, size)
        else
            GZIPInputStream(inputStream)
}