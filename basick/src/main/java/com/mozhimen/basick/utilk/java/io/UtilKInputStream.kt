package com.mozhimen.basick.utilk.java.io

import android.text.TextUtils
import com.mozhimen.basick.utilk.bases.IUtilK
import java.io.InputStream

/**
 * @ClassName UtilKInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 11:42
 * @Version 1.0
 */

fun InputStream.getAvailableLong(): Long =
    UtilKInputStream.getAvailableLong(this)

fun InputStream.readBytesForInputStream(bytes: ByteArray) {
    UtilKInputStream.readBytesForInputStream(this, bytes)
}

////////////////////////////////////////////////////////////////////////////

object UtilKInputStream : IUtilK {

    @JvmStatic
    fun getAvailableLong(inputStream: InputStream): Long =
        inputStream.use { it.available().toLong() }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isInputStreamSame(inputStream1: InputStream, inputStream2: InputStream): Boolean =
        TextUtils.equals(inputStream1.inputStream2strMd52(), inputStream2.inputStream2strMd52())

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun readBytesForInputStream(inputStream: InputStream, bytes: ByteArray) {
        inputStream.use { inputStream.read(bytes) }
    }
}