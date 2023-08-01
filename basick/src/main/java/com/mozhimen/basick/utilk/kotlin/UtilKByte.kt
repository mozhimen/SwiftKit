package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKByte
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:19
 * @Version 1.0
 */
fun Byte.asHexStr(): String =
    UtilKByte.byte2hexStr(this)

object UtilKByte {
    /**
     * 字节转成字符.
     * @param byte 原始字节.
     * @return 转换后的字符.
     */
    @JvmStatic
    fun byte2hexStr(byte: Byte): String {
        val hexs = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")
        return hexs[(byte.toInt() and 0xf0) shr 4] + hexs[byte.toInt() and 0x0f]
    }
}