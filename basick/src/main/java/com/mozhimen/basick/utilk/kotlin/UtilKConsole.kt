package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName UtilKConsole
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/31 11:19
 * @Version 1.0
 */
fun <T> T.print() {
    UtilKConsole.print(this)
}

fun <T> T.println() {
    UtilKConsole.println(this)
}

fun <T> T.printlog() {
    UtilKConsole.printlog(this)
}

fun <T> T.printlog(tag: String) {
    UtilKConsole.printlog(tag, this)
}

fun String.printlogEach() {
    UtilKConsole.printlogEach(this)
}

object UtilKConsole : IUtilK {
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
    fun printlogEach(msg: String) {
        for (char in msg)
            printlog(TAG, char)
    }

    @JvmStatic
    fun <T> printlog(tag: String, msg: T) {
        println("$tag: $msg")
    }

}