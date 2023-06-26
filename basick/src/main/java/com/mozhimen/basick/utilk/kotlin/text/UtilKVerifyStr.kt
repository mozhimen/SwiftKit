package com.mozhimen.basick.utilk.kotlin.text

/**
 * @ClassName UtilKVerifyNumber
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/26 16:59
 * @Version 1.0
 */
fun String.checkAllDigits(): Boolean =
    UtilKVerifyStr.checkAllDigits(this)

fun String.checkAllDigits2(): Boolean =
    UtilKVerifyStr.checkAllDigits2(this)

fun String.checkAllDigitsAndAlphabets(): Boolean =
    UtilKVerifyStr.checkAllDigitsAndAlphabets(this)

object UtilKVerifyStr {
    /**
     * 是否是数字
     * @param str String
     */
    @JvmStatic
    fun checkAllDigits(str: String): Boolean =
        str.matches(Regex("^[0-9]*\$"))

    /**
     * 是否是数字
     * @param str String
     * @return Boolean
     */
    @JvmStatic
    fun checkAllDigits2(str: String) =
        str.matches(Regex("[0-9]+"))

    /**
     * 同时包含数字和字母
     * @param str String
     * @return Boolean
     */
    @JvmStatic
    fun checkAllDigitsAndAlphabets(str: String): Boolean =
        str.matches(Regex("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{2,}\$"))
}