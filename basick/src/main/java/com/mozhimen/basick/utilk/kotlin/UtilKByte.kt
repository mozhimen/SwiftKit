package com.mozhimen.basick.utilk.kotlin

import kotlin.experimental.and

/**
 * @ClassName UtilKByte
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:19
 * @Version 1.0
 */
fun Byte.byte2strHex(): String =
    UtilKByte.byte2strHex(this)

fun Byte.byte2strHex_ofHexString(): String =
    UtilKByte.byte2strHex(this)

fun Byte.byte2int(): Int =
    UtilKByte.byte2int(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKByte {
    val strHexs by lazy { arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f") }

    //字节转成字符
    @JvmStatic
    fun byte2strHex(byte: Byte): String =
        strHexs[(byte.toInt() and 0xf0) shr 4] + strHexs[byte.toInt() and 0x0f]

    @JvmStatic
    fun byte2strHex_ofHexString(byte: Byte): String {
        val v: Int = ((byte and 0xFF.toByte()).toInt())
        val hv: String = Integer.toHexString(v)
        return if (hv.length < 2)
            "0"
        else hv
    }

    @JvmStatic
    fun byte2int(byte: Byte): Int =
        if (byte <= '9'.toByte()) byte - '0'.toByte() else byte - 'a'.toByte() + 10
}