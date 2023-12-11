package com.mozhimen.basick.utilk.kotlin.text

import com.mozhimen.basick.elemk.cons.CMsg
import java.io.File

/**
 * @ClassName UtilKRegex
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:18
 * @Version 1.0
 */
fun String.replaceDot(): String =
    UtilKRegex.replaceDot(this)

fun String.replaceRegexLineBreak(): String =
    UtilKRegex.replaceRegexLineBreak(this)

fun String.replaceRegexDoubleQuote(): String =
    UtilKRegex.replaceRegexDoubleQuote(this)

fun String.replaceEndSeparator(): String =
    UtilKRegex.replaceEndSeparator(this)


object UtilKRegex {
    @JvmStatic
    fun replaceDot(str: String): String =
        str.replace(",", ".")

    @JvmStatic
    fun replaceRegexLineBreak(str: String): String =
        str.replace("\\n".toRegex(), CMsg.LINE_BREAK)

    @JvmStatic
    fun replaceRegexDoubleQuote(str: String): String =
        str.replace("\"".toRegex(), "")

    @JvmStatic
    fun replaceEndSeparator(str: String): String =
        if (str.endsWith("/")) str.substring(0, str.length - 1) else str
}