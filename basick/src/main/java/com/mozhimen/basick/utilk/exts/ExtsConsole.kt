package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.java.UtilKConsole

/**
 * @ClassName ExtsKConsole
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/31 11:21
 * @Version 1.0
 */
fun <T> T.print() =
    UtilKConsole.print(this)

fun <T> T.println() =
    UtilKConsole.println(this)

fun <T> T.printlog() =
    UtilKConsole.printlog(this)

fun <T> T.printlog(tag: String) =
    UtilKConsole.printlog(tag, this)