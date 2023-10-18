package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKStrHex
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 1:07
 * @Version 1.0
 */
fun String.strHex2bytes(): ByteArray =
    UtilKStrHex.strHex2bytes(this)

object UtilKStrHex {
    @JvmStatic
    fun strHex2bytes(strHex: String): ByteArray {
        if (strHex.isEmpty()) return ByteArray(0)
        val bytes = strHex.toByteArray()
        val n = bytes.size shr 1
        val buf = ByteArray(n)
        for (i in 0 until n) {
            val index = i shl 1
            buf[i] = (bytes[index].byte2int() shl 4 or bytes[index + 1].byte2int()).toByte()
        }
        return buf
    }
}