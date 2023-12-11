package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKVerifyString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/19 11:08
 * @Version 1.0
 */
fun String.filterNumber(): String =
        UtilKFilter.filterNumber(this)

fun String.filterAlphabet(): String =
        UtilKFilter.filterAlphabet(this)

fun String.filterChinese(): String =
        UtilKFilter.filterChinese(this)

fun String.filterNAC(): String =
        UtilKFilter.filterNAC(this)

fun String.filterLength(endIndex: Int): String =
        UtilKFilter.filterLength(this, endIndex)

object UtilKFilter {
    /**
     * 过滤出数字
     */
    @JvmStatic
    fun filterNumber(number: String): String =
        number.replace("[^(0-9)]".toRegex(), "")

    /**
     * 过滤出字母
     */
    @JvmStatic
    fun filterAlphabet(alphabet: String): String =
        alphabet.replace("[^(A-Za-z)]".toRegex(), "")

    /**
     * 过滤出中文
     */
    @JvmStatic
    fun filterChinese(chinese: String): String =
        chinese.replace("[^(\\u4e00-\\u9fa5)]".toRegex(), "")

    /**
     * 过滤出字母、数字和中文
     */
    @JvmStatic
    fun filterNAC(character: String): String =
        character.replace("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]".toRegex(), "")

    /**
     * 过滤长度
     */
    @JvmStatic
    fun filterLength(str: String, endIndex: Int): String =
        if (endIndex in str.indices) str.substring(0, endIndex) else str
}