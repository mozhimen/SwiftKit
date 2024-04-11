package com.mozhimen.basick.utilk.android.util

import android.util.Base64

/**
 * @ClassName UtilKBase64
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/10
 * @Version 1.0
 */
object UtilKBase64 {
    @JvmStatic
    fun encodeToString(bytes: ByteArray, flags: Int): String =
        Base64.encodeToString(bytes, flags)

    @JvmStatic
    fun encode(bytes: ByteArray, flags: Int): ByteArray =
        Base64.encode(bytes, flags)

    @JvmStatic
    fun decode(bytes: ByteArray, flags: Int): ByteArray =
        Base64.decode(bytes, flags)
}