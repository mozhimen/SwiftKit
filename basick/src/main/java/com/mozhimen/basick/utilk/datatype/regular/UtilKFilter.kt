package com.mozhimen.basick.utilk.datatype.regular


/**
 * @ClassName UtilKVerifyString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/19 11:08
 * @Version 1.0
 */
object UtilKFilter {
    /**
     * 过滤出数字
     * @param number String
     * @return String
     */
    fun filterNumber(number: String): String =
        number.replace("[^(0-9)]".toRegex(), "")

    /**
     * 过滤出字母
     * @param alphabet String
     * @return String
     */
    fun filterAlphabet(alphabet: String): String =
        alphabet.replace("[^(A-Za-z)]".toRegex(), "")


    /**
     * 过滤出中文
     * @param chinese String
     * @return String
     */
    fun filterChinese(chinese: String): String =
        chinese.replace("[^(\\u4e00-\\u9fa5)]".toRegex(), "")

    /**
     * 过滤出字母、数字和中文
     * @param character String
     * @return String
     */
    fun filter(character: String): String =
        character.replace("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]".toRegex(), "")

    /**
     * 过滤长度
     * @param str String
     * @param endIndex Int
     * @return String
     */
    fun filterLength(str: String, endIndex: Int): String =
        if (endIndex in str.indices) str.substring(0, endIndex) else str
}