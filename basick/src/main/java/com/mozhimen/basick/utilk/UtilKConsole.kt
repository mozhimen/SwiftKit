package com.mozhimen.basick.utilk

/**
 * @ClassName UtilKConsole
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/31 11:19
 * @Version 1.0
 */
object UtilKConsole {
    const val TAG = "UtilKConsole>>>>>"

    @JvmStatic
    fun <T> print(msg: T) {
        kotlin.io.print(msg)
    }

    @JvmStatic
    fun <T> println(msg: T) {
        kotlin.io.println(msg)
    }

    @JvmStatic
    fun <T> printlog(msg: T) {
        printlog(TAG, msg)
    }

    @JvmStatic
    fun <T> printlog(tag: String, msg: T) {
        kotlin.io.println("$tag: $msg")
    }
}