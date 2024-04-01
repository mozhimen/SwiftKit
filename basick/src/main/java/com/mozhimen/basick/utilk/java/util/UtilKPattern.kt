package com.mozhimen.basick.utilk.java.util

import java.util.regex.Pattern

/**
 * @ClassName UtilKPattern
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/1
 * @Version 1.0
 */
object UtilKPattern {
    @JvmStatic
    fun get(regex: String): Pattern =
        compile(regex)

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun compile(regex: String): Pattern =
        Pattern.compile(regex)
}