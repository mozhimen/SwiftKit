package com.mozhimen.basick.utilk.java.io

import java.io.InputStream
import java.io.PushbackInputStream

/**
 * @ClassName UtilKPushbackInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKPushbackInputStream {
    @JvmStatic
    fun get(inputStream: InputStream, size: Int? = null): PushbackInputStream =
        if (size != null && size > 0)
            PushbackInputStream(inputStream, size)
        else
            PushbackInputStream(inputStream)
}