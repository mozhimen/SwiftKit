package com.mozhimen.basick.utilk.regular


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
    fun isNumberic(str: String): Boolean {
        val reg = Regex("^[0-9]*\$")
        return str.matches(reg)
    }
}