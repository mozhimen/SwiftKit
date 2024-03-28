package com.mozhimen.basick.utilk.java.io

import java.io.BufferedInputStream
import java.io.InputStream

/**
 * @ClassName UtilKBufferedInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
object UtilKBufferedInputStream {
    @JvmStatic
    fun get(inputStream: InputStream): BufferedInputStream =
        BufferedInputStream(inputStream)
}