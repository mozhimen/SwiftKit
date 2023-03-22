package com.mozhimen.basick.utilk.java.datatype.regular


/**
 * @ClassName UtilKVerifyNumber
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/26 16:59
 * @Version 1.0
 */
object UtilKVerifyString {
    /**
     * 是否是数字
     * @param str String
     */
    @JvmStatic
    fun checkAllNumberic(str: String): Boolean =
        str.matches(Regex("^[0-9]*\$"))

    /**
     * 同时包含数字和字母
     * @param str String
     * @return Boolean
     */
    fun checkAllNumberAndAlphabet(str: String): Boolean =
        str.matches(Regex("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{2,}\$"))
}