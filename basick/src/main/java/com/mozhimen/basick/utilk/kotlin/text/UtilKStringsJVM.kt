package com.mozhimen.basick.utilk.kotlin.text

import com.mozhimen.basick.elemk.cons.CMsg

/**
 * @ClassName UtilKFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/2
 * @Version 1.0
 */
fun String.replaceLineBreak2strHtmlBr(): String =
    UtilKStringsJVM.replaceLineBreak2strHtmlBr(this)

fun String.replaceDot(): String =
    UtilKStringsJVM.replaceDot(this)

fun String.removeLineBreakStr(): String =
    UtilKStringsJVM.removeLineBreakStr(this)

fun String.removeLineBreak(): String =
    UtilKStringsJVM.removeLineBreak(this)

fun String.removeEndLineBreak(): String =
    UtilKStringsJVM.removeEndLineBreak(this)

fun String.removeStartLineBreak(): String =
    UtilKStringsJVM.removeStartLineBreak(this)

fun String.removeEndSeparator(): String =
    UtilKStringsJVM.removeEndSeparator(this)

fun String.removeStartSeparator(): String =
    UtilKStringsJVM.removeStartSeparator(this)

fun String.complementStart0(): String =
    UtilKStringsJVM.complementStart0(this)

fun String.complementStartPlus(): String =
    UtilKStringsJVM.complementStartPlus(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKStringsJVM {
    @JvmStatic
    fun replaceLineBreak2strHtmlBr(str: String): String =
        str.replace(CMsg.LINE_BREAK, "<br>")

    @JvmStatic
    fun replaceDot(str: String): String =
        str.replace(",", ".")

    @JvmStatic
    fun removeLineBreakStr(str: String): String =
        str.replace(CMsg.LINE_BREAK_STR, "")

    @JvmStatic
    fun removeLineBreak(str: String): String =
        str.replace(CMsg.LINE_BREAK, "")

    @JvmStatic
    fun removeEndLineBreak(str: String): String =
        if (str.endsWith(CMsg.LINE_BREAK)) str.substring(0, str.length - 1) else str

    @JvmStatic
    fun removeStartLineBreak(str: String): String =
        if (str.startsWith(CMsg.LINE_BREAK)) str.substring(1) else str

    @JvmStatic
    fun removeEndSeparator(str: String): String =
        if (str.endsWith("/")) str.substring(0, str.length - 1) else str

    @JvmStatic
    fun removeStartSeparator(str: String): String =
        if (str.startsWith("/")) str.substring(1) else str

    @JvmStatic
    fun complementStart0(str: String): String =
        if (str.startsWith(".")) "0$str" else str

    @JvmStatic
    fun complementStartPlus(str: String): String =
        if (!str.startsWith("+")) "+$str" else str
}