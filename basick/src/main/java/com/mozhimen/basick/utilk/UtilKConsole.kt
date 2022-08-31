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

    fun print(msg: String) {
        kotlin.io.print(msg)
    }

    fun println(msg: String) {
        kotlin.io.println(msg)
    }

    fun printlog(msg: String) {
        printlog(TAG, msg)
    }

    fun printlog(tag: String, msg: String) {
        kotlin.io.println("$tag: $msg")
    }
}