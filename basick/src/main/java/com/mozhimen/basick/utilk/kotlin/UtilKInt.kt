package com.mozhimen.basick.utilk.kotlin

import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.java.lang.UtilKCharacter

/**
 * @ClassName UtilKInt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 10:41
 * @Version 1.0
 */
fun Int.int2boolean(): Boolean =
    UtilKInt.int2boolean(this)

fun Int.intAscii2int(): Int =
    UtilKInt.intAscii2int(this)

fun Int.intByte2strByte(): String =
    UtilKInt.intByte2strByte(this)

fun Int.getStrByte(@IntRange(from = 1) digit: Int): String =
    UtilKInt.getStrByte(this, digit)

object UtilKInt {

    @JvmStatic
    fun int2boolean(int: Int) =
        int == 1

    /**
     * ASCII转整型
     * '5' ascci 是 53。 输入 int 53，输出 int 5
     */
    @JvmStatic
    fun intAscii2int(intAscii: Int): Int =
        UtilKCharacter.getNumericValue(intAscii)

    @JvmStatic
    fun intByte2strByte(intByte: Int): String =
        Integer.toBinaryString(intByte)

    ////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrByte(intByte: Int, @IntRange(from = 1) digit: Int): String {
        var strByte = String.format("%${digit}s", intByte.intByte2strByte()).replace(" ", "0")
        if (strByte.length > digit)
            strByte = strByte.substring(strByte.length - digit, strByte.length)
        return strByte
    }

//    fun getByteStr(byteInt: Int, @IntRange(from = 1) digit: Int) {
//        val binaryString = String.format("%0${bit}s", Integer.toBinaryString(num))
//        for (i in binaryString.indices) {
//            val bit = binaryString[i]
//            "Bit at position $i is $bit".printlog()
//        }
//    }
}