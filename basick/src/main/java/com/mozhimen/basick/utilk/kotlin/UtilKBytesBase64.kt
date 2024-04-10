package com.mozhimen.basick.utilk.kotlin

import android.util.Base64
import com.mozhimen.basick.elemk.android.util.cons.CBase64
import com.mozhimen.basick.utilk.android.util.UtilKBase64

/**
 * @ClassName UtilKBytesBase64
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:34
 * @Version 1.0
 */
fun ByteArray.bytes2strBase64(flags: Int = CBase64.NO_WRAP): String =
    UtilKBytesBase64.bytes2strBase64(this, flags)

fun ByteArray.bytes2bytesBase64(flags: Int): ByteArray =
    UtilKBytesBase64.bytes2bytesBase64(this, flags)

fun ByteArray.bytesBase642bytes(flags: Int = CBase64.DEFAULT): ByteArray =
    UtilKBytesBase64.bytesBase642bytes(this, flags)

//////////////////////////////////////////////////////////////////////

object UtilKBytesBase64 {
    @JvmStatic
    fun bytes2strBase64(bytesBase64: ByteArray, flags: Int = CBase64.NO_WRAP): String =
        UtilKBase64.encodeToString(bytesBase64, flags)

    @JvmStatic
    fun bytes2bytesBase64(bytesBase64: ByteArray, flags: Int): ByteArray =
        UtilKBase64.encode(bytesBase64, flags)

    @JvmStatic
    fun bytesBase642bytes(bytesBase64: ByteArray, flags: Int = CBase64.DEFAULT): ByteArray =
        UtilKBase64.decode(bytesBase64, flags)
}