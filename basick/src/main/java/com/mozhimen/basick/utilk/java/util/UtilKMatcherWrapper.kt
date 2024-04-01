package com.mozhimen.basick.utilk.java.util

import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @ClassName UtilKPattern
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/19 19:33
 * @Version 1.0
 */
fun String.strHump2strUnderline(): String =
    UtilKMatcherWrapper.strHump2strUnderline(this)

fun String.strUnderline2strHump(): String =
    UtilKMatcherWrapper.strUnderline2strHump(this)

object UtilKMatcherWrapper {
    /**
     * Hump to underline
     * 驼峰转下划线
     */
    @JvmStatic
    fun strHump2strUnderline(str: String): String {
        var tempStr = str
        val matcher: Matcher = UtilKMatcher.get("([A-Z])", tempStr)
        while (matcher.find()) {
            val target = matcher.group()
            tempStr = tempStr.replace(target.toRegex(), "_" + target.lowercase(Locale.getDefault()))
        }
        if (tempStr.first() == '_') tempStr.replaceFirst("_", "")
        return tempStr
    }

    /**
     * Underline to hump
     * 下划线转驼峰
     */
    @JvmStatic
    fun strUnderline2strHump(str: String): String {
        var tempStr = str
        val matcher: Matcher = UtilKMatcher.get("_(.)", tempStr)
        while (matcher.find()) {
            val target = matcher.group(1)
            if (target != null) {
                tempStr = tempStr.replace("_$target".toRegex(), target.uppercase(Locale.getDefault()))
            }
        }
        return tempStr
    }
}