package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKByte
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:19
 * @Version 1.0
 */
fun Byte.byte2strHex(): String =
    UtilKByte.byte2strHex(this)

fun Byte.byte2int(): Int =
    UtilKByte.byte2int(this)

object UtilKByte {
    /**
     * 字节转成字符.
     * @param byte 原始字节.
     * @return 转换后的字符.
     */
    @JvmStatic
    fun byte2strHex(byte: Byte): String {
        val strHexs = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")
        return strHexs[(byte.toInt() and 0xf0) shr 4] + strHexs[byte.toInt() and 0x0f]
    }

    @JvmStatic
    fun byte2int(byte: Byte): Int =
        if (byte <= '9'.toByte()) byte - '0'.toByte() else byte - 'a'.toByte() + 10
}