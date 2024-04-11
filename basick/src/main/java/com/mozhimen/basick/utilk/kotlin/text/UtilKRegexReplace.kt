package com.mozhimen.basick.utilk.kotlin.text

import com.mozhimen.basick.elemk.cons.CMsg

/**
 * @ClassName UtilKRegex
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:18
 * @Version 1.0
 */
fun String.replaceRegexLineBreak(): String =
    UtilKRegexReplace.replaceRegexLineBreak(this)

fun String.removeRegexDoubleQuote(): String =
    UtilKRegexReplace.removeRegexDoubleQuote(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKRegexReplace {
    @JvmStatic
    fun replaceRegexLineBreak(str: String): String =
        str.replace("\\n".toRegex(), CMsg.LINE_BREAK)

    @JvmStatic
    fun removeRegexDoubleQuote(str: String): String =
        str.replace("\"".toRegex(), "")
}