package com.mozhimen.basick.utilk.java.io

import android.text.TextUtils
import com.mozhimen.basick.utilk.commons.IUtilK
import java.io.InputStream

/**
 * @ClassName UtilKInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 11:42
 * @Version 1.0
 */

fun InputStream.getAvailableLong_use(): Long =
    UtilKInputStream.getAvailableLong_use(this)

fun InputStream.read_use(bytes: ByteArray): Int =
    UtilKInputStream.read_use(this, bytes)

////////////////////////////////////////////////////////////////////////////

object UtilKInputStream : IUtilK {

    @JvmStatic
    fun getAvailableLong_use(inputStream: InputStream): Long =
        inputStream.use { it.available().toLong() }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isInputStreamSame_use(inputStream1: InputStream, inputStream2: InputStream): Boolean =
        TextUtils.equals(inputStream1.inputStream2strMd5_use_ofBigInteger(), inputStream2.inputStream2strMd5_use_ofBigInteger())

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun read_use(inputStream: InputStream, bytes: ByteArray): Int =
        inputStream.use { inputStream.read(bytes) }
}