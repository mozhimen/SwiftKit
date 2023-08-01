package com.mozhimen.basick.utilk.kotlin

import androidx.annotation.IntRange

/**
 * @ClassName UtilKInt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 10:41
 * @Version 1.0
 */
fun Int.getByteStr(): String =
    UtilKInt.getByteStr(this)

fun Int.getByteStr(@IntRange(from = 1) digit: Int): String =
    UtilKInt.getByteStr(this, digit)

fun Int.asBoolean(): Boolean =
    UtilKInt.int2Boolean(this)

object UtilKInt {
//    fun getByteStr(byteInt: Int, @IntRange(from = 1) digit: Int) {
//        val binaryString = String.format("%0${bit}s", Integer.toBinaryString(num))
//        for (i in binaryString.indices) {
//            val bit = binaryString[i]
//            "Bit at position $i is $bit".printlog()
//        }
//    }

    fun int2Boolean(int: Int) =
        int == 1

    fun getByteStr(byteInt: Int): String =
        Integer.toBinaryString(byteInt)

    fun getByteStr(byteInt: Int, @IntRange(from = 1) digit: Int): String {
        var byteStr = String.format("%${digit}s", Integer.toBinaryString(byteInt)).replace(" ", "0")
        if (byteStr.length > digit) byteStr = byteStr.substring(byteStr.length - digit, byteStr.length)
        return byteStr
    }
}