package com.mozhimen.basick.utilk.kotlin.text

import com.mozhimen.basick.elemk.cons.CMsg

/**
 * @ClassName UtilKFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/2
 * @Version 1.0
 */
fun String.replaceDot(): String =
    UtilKStringsJVM.replaceDot(this)

fun String.replaceLineBreakStr(): String =
    UtilKStringsJVM.replaceLineBreakStr(this)

fun String.replaceLineBreak(): String =
    UtilKStringsJVM.replaceLineBreak(this)

fun String.substringEndSeparator(): String =
    UtilKStringsJVM.substringEndSeparator(this)

fun String.substringStartSeparator(): String =
    UtilKStringsJVM.substringStartSeparator(this)

fun String.complementStart0(): String =
    UtilKStringsJVM.complementStart0(this)

fun String.complementStartPlus(): String =
    UtilKStringsJVM.complementStartPlus(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKStringsJVM {
    @JvmStatic
    fun replaceDot(str: String): String =
        str.replace(",", ".")

    @JvmStatic
    fun replaceLineBreakStr(str: String): String =
        str.replace(CMsg.LINE_BREAK_STR, "")

    @JvmStatic
    fun replaceLineBreak(str: String): String =
        str.replace(CMsg.LINE_BREAK, "")

    @JvmStatic
    fun substringEndSeparator(str: String): String =
        if (str.endsWith("/")) str.substring(0, str.length - 1) else str

    @JvmStatic
    fun substringStartSeparator(str: String): String =
        if (str.startsWith("/")) str.substring(1) else str

    @JvmStatic
    fun complementStart0(str: String): String =
        if (str.startsWith(".")) "0$str" else str

    @JvmStatic
    fun complementStartPlus(str: String): String =
        if (!str.startsWith("+")) "+$str" else str
}