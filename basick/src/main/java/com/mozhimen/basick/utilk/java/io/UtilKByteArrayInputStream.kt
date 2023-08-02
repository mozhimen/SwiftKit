package com.mozhimen.basick.utilk.java.io

import java.io.ByteArrayInputStream

/**
 * @ClassName UtilKByteArrayInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 13:16
 * @Version 1.0
 */
fun ByteArray.bytes2byteArrayInputStream(): ByteArrayInputStream =
        UtilKByteArrayInputStream.bytes2byteArrayInputStream(this)

object UtilKByteArrayInputStream {
    @JvmStatic
    fun bytes2byteArrayInputStream(bytes: ByteArray): ByteArrayInputStream =
            ByteArrayInputStream(bytes)
}