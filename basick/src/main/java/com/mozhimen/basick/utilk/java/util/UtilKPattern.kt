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
    UtilKPattern.strHump2strUnderline(this)

fun String.strUnderline2strHump(): String =
    UtilKPattern.strUnderline2strHump(this)

object UtilKPattern {
    /**
     * Hump to underline
     * 驼峰转下划线
     * @param str
     * @return
     */
    @JvmStatic
    fun strHump2strUnderline(str: String): String {
        var newStr = str
        val matcher: Matcher = Pattern.compile("([A-Z])").matcher(newStr)
        while (matcher.find()) {
            val target = matcher.group()
            newStr = newStr.replace(target.toRegex(), "_" + target.lowercase(Locale.getDefault()))
        }
        if (newStr.first() == '_') newStr.replaceFirst("_", "")
        return newStr
    }

    /**
     * Underline to hump
     * 下划线转驼峰
     * @param str
     * @return
     */
    @JvmStatic
    fun strUnderline2strHump(str: String): String {
        var newStr = str
        val matcher: Matcher = Pattern.compile("_(.)").matcher(newStr)
        while (matcher.find()) {
            val target = matcher.group(1)
            if (target != null) {
                newStr = newStr.replace("_$target".toRegex(), target.uppercase(Locale.getDefault()))
            }
        }
        return newStr
    }
}