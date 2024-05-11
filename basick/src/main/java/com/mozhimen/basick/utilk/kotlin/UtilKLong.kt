package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKLong
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/11
 * @Version 1.0
 */
fun Long.kiloBytes(): Long =
    UtilKLong.kiloBytes(this)

fun Long.megaBytes(): Long =
    UtilKLong.megaBytes(this)

fun Long.gigaBytes(): Long =
    UtilKLong.gigaBytes(this)

//////////////////////////////////////////////////////////

object UtilKLong {
    @JvmStatic
    fun kiloBytes(long: Long): Long =
        long * 1000L

    @JvmStatic
    fun megaBytes(long: Long): Long =
        kiloBytes(long) * 1000L

    @JvmStatic
    fun gigaBytes(long: Long): Long =
        megaBytes(long) * 1000L
}