package com.mozhimen.basick.utilk.kotlin.text

/**
 * @ClassName UtilKRegexMatch
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/10
 * @Version 1.0
 */
fun String.matches_ofStrDigits(): Boolean =
    UtilKRegexMatch.matches_ofStrDigits(this)

fun String.matches_ofStrDigits2(): Boolean =
    UtilKRegexMatch.matches_ofStrDigits2(this)

fun String.matches_ofStrDigitsAndAlphabets(): Boolean =
    UtilKRegexMatch.matches_ofStrDigitsAndAlphabets(this)

//////////////////////////////////////////////////////////////////

fun String.matches_ofStrIp(): Boolean =
    UtilKRegexMatch.matches_ofStrIp(this)

fun String.matches_ofStrDomain(): Boolean =
    UtilKRegexMatch.matches_ofStrDomain(this)

fun String.matches_ofStrPort(): Boolean =
    UtilKRegexMatch.matches_ofStrPort(this)

//////////////////////////////////////////////////////////////////

object UtilKRegexMatch {

    //region number
    //是否是数字
    @JvmStatic
    fun matches_ofStrDigits(str: String): Boolean =
        str.matches(Regex("^[0-9]*\$"))

    //是否是数字
    @JvmStatic
    fun matches_ofStrDigits2(str: String) =
        str.matches(Regex("[0-9]+"))

    //同时包含数字和字母
    @JvmStatic
    fun matches_ofStrDigitsAndAlphabets(str: String): Boolean =
        str.matches(Regex("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{2,}\$"))
    //endregion

    //////////////////////////////////////////////////////////////////

    //region # card
    /**
     * 身份证校验
     */
    @JvmStatic
    fun matches_ofStrIdCard(strId: String): Boolean =
        strId.matches(Regex("^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\\d{4}(([1][9]\\d{2})|([2]\\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\\d{3}[0-9xX]$"))

    /**
     * 港澳居民来往内地通行证校验
     * 规则： H/M + 10位或6位数字
     * 样本： H1234567890
     */
    @JvmStatic
    fun matches_ofStrIdCardHk(strId: String): Boolean =
        strId.matches(Regex("^([A-Z]\\d{6,10}(\\(\\w{1}\\))?)\$"))

    /**
     * 台湾居民来往大陆通行证校验
     * 规则： 新版8位或18位数字， 旧版10位数字 + 英文字母
     * 样本： 12345678 或 1234567890B
     */
    @JvmStatic
    fun matches_ofStrIdCardTw(strId: String): Boolean =
        strId.matches(Regex("^\\d{8}|^[a-zA-Z0-9]{10}|^\\d{18}\$"))

    /**
     * 护照校验
     * 规则： 14/15开头 + 7位数字, G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
     * 样本： 141234567, G12345678, P1234567
     */
    @JvmStatic
    fun matches_ofStrIdCardPp(strId: String): Boolean =
        strId.matches(Regex("^([a-zA-z]|[0-9]){5,17}\$"))
    //endregion

    //////////////////////////////////////////////////////////////////

    //ip是否合法
    @JvmStatic
    fun matches_ofStrIp(strIp: String) =
        strIp.matches(Regex("((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"))

    //域名是否合法
    @JvmStatic
    fun matches_ofStrDomain(strDomain: String) =
        strDomain.matches(Regex("^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$"))

    //端口是否合法
    @JvmStatic
    fun matches_ofStrPort(strPort: String) =
        strPort.matches(Regex("^[-+]?[\\d]{1,6}$"))
}