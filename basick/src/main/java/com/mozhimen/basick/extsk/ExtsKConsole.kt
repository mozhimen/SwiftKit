package com.mozhimen.basick.extsk

import com.mozhimen.basick.utilk.UtilKConsole

/**
 * @ClassName ExtsKConsole
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/31 11:21
 * @Version 1.0
 */
fun String.print() =
    UtilKConsole.print(this)

fun String.println() =
    UtilKConsole.println(this)

fun String.printlog() =
    UtilKConsole.printlog(this)

fun String.printlog(tag: String) =
    UtilKConsole.printlog(tag, this)