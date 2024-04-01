package com.mozhimen.basick.utilk.java.util

import java.util.regex.Matcher

/**
 * @ClassName UtilKMatcher
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/1
 * @Version 1.0
 */
object UtilKMatcher {
    @JvmStatic
    fun get(regex: String, str: String): Matcher =
        matcher(regex, str)

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun matcher(regex: String, str: String): Matcher =
        UtilKPattern.compile(regex).matcher(str)
}