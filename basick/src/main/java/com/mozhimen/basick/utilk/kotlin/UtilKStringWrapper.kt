package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKStringWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/11
 * @Version 1.0
 */
object UtilKStringWrapper {
    @JvmStatic
    fun getStr_ofCompute(str: String): String {
        val sanitizedName = str
            .replace(Regex("\\(.*\\)"), "")

        return sanitizedName.asSequence()
            .filter { it.isDigit() or it.isUpperCase() or (it == '&') }
            .take(3)
            .joinToString("")
            .ifBlank { str.first().toString() }
            .capitalize()
    }
}