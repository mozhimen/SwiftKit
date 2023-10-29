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
        val bytesHex = strHex.toByteArray()
        val n = bytesHex.size shr 1
        val bytes = ByteArray(n)
        for (i in 0 until n) {
            val index = i shl 1
            bytes[i] = (bytesHex[index].byte2int() shl 4 or bytesHex[index + 1].byte2int()).toByte()
        }
        return bytes
    }
}