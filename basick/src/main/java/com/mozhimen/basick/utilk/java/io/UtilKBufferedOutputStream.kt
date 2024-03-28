package com.mozhimen.basick.utilk.java.io

import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream

/**
 * @ClassName UtilKBufferedOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
object UtilKBufferedOutputStream {
    @JvmStatic
    fun get(outputStream: OutputStream): BufferedOutputStream =
        BufferedOutputStream(outputStream)
}