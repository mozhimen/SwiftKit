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
        UtilKRegex.replaceRegexLineBreak(this)

object UtilKRegex {
    fun replaceRegexLineBreak(str:String):String =
            str.replace("\\n".toRegex(), CMsg.LINE_BREAK)
}