package com.mozhimen.basicsmk.utilmk

/**
 * @ClassName UtilMKIdentityCard
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 14:06
 * @Version 1.0
 */
object UtilMKVerifyCard {
    enum class CardType {
        ID_CARD, HK_CARD, TW_CARD, PP_CARD
    }

    //身份证校验
    fun isIdCardValid(id: String): Boolean {
        val reg =
            Regex("^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\\d{4}(([1][9]\\d{2})|([2]\\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\\d{3}[0-9xX]$")
        return id.matches(reg)
    }

    fun isHKCardValid(id: String): Boolean {
        // 港澳居民来往内地通行证
        // 规则： H/M + 10位或6位数字
        // 样本： H1234567890
        val reg = Regex("^([A-Z]\\d{6,10}(\\(\\w{1}\\))?)\$")
        return id.matches(reg)
    }

    fun isTWCardValid(id: String): Boolean {
        // 台湾居民来往大陆通行证
        // 规则： 新版8位或18位数字， 旧版10位数字 + 英文字母
        // 样本： 12345678 或 1234567890B
        val reg = Regex("^\\d{8}|^[a-zA-Z0-9]{10}|^\\d{18}\$")
        return id.matches(reg)
    }

    fun isPPCardValid(id: String): Boolean {
        // 护照
        // 规则： 14/15开头 + 7位数字, G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
        // 样本： 141234567, G12345678, P1234567
        val reg = Regex("^([a-zA-z]|[0-9]){5,17}\$")
        return id.matches(reg)
    }
}