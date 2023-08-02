package com.mozhimen.basick.utilk.kotlin.text

/**
 * @ClassName UtilKVerifyNumber
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/26 16:59
 * @Version 1.0
 */
fun String.isStrDigits(): Boolean =
    UtilKMatchStr.isStrDigits(this)

fun String.isStrDigits2(): Boolean =
    UtilKMatchStr.isStrDigits2(this)

fun String.isStrDigitsAndAlphabets(): Boolean =
    UtilKMatchStr.isStrDigitsAndAlphabets(this)

object UtilKMatchStr {
    /**
     * 是否是数字
     * @param str String
     */
    @JvmStatic
    fun isStrDigits(str: String): Boolean =
        str.matches(Regex("^[0-9]*\$"))

    /**
     * 是否是数字
     * @param str String
     * @return Boolean
     */
    @JvmStatic
    fun isStrDigits2(str: String) =
        str.matches(Regex("[0-9]+"))

    /**
     * 同时包含数字和字母
     * @param str String
     * @return Boolean
     */
    @JvmStatic
    fun isStrDigitsAndAlphabets(str: String): Boolean =
        str.matches(Regex("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{2,}\$"))
}