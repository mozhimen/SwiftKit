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
fun Int.getStrByte(@IntRange(from = 1) digit: Int): String =
    UtilKInt.getStrByte(this, digit)

object UtilKInt {

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