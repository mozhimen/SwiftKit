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

fun String.removeEnd_ofLineBreak(): String =
    UtilKStringsJVM.removeEnd_ofLineBreak(this)

fun String.removeStart_ofLineBreak(): String =
    UtilKStringsJVM.removeStart_ofLineBreak(this)

fun String.removeEnd_ofSeparator(): String =
    UtilKStringsJVM.removeEnd_ofSeparator(this)

fun String.removeStart_ofSeparator(): String =
    UtilKStringsJVM.removeStart_ofSeparator(this)

fun String.addStart_of0(): String =
    UtilKStringsJVM.addStart_of0(this)

fun String.addStart_ofPlus(): String =
    UtilKStringsJVM.addStart_ofPlus(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKStringsJVM {
    @JvmStatic
    fun replaceLineBreak2strHtmlBr(str: String): String =
        str.replace(CMsg.LINE_BREAK, "<br>")

    @JvmStatic
    fun replaceDot(str: String): String =
        str.replace(",", ".")

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun removeLineBreakStr(str: String): String =
        str.replace(CMsg.LINE_BREAK_STR, "")

    @JvmStatic
    fun removeLineBreak(str: String): String =
        str.replace(CMsg.LINE_BREAK, "")

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun removeEnd_ofLineBreak(str: String): String =
        if (str.endsWith(CMsg.LINE_BREAK)) str.substring(0, str.length - 1) else str

    @JvmStatic
    fun removeEnd_ofSeparator(str: String): String =
        if (str.endsWith("/")) str.substring(0, str.length - 1) else str

    @JvmStatic
    fun removeStart_ofLineBreak(str: String): String =
        if (str.startsWith(CMsg.LINE_BREAK)) str.substring(1) else str

    @JvmStatic
    fun removeStart_ofSeparator(str: String): String =
        if (str.startsWith("/")) str.substring(1) else str

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun addStart_of0(str: String): String =
        if (str.startsWith(".")) "0$str" else str

    @JvmStatic
    fun addStart_ofPlus(str: String): String =
        if (!str.startsWith("+")) "+$str" else str
}