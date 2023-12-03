package com.mozhimen.uicorek.textk.edit.bar.mos

import androidx.annotation.StringRes
import java.io.Serializable

/**
 * @ClassName MTextKEditBarConfig
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/3 16:05
 * @Version 1.0
 */

class MTextKEditBarConfig : Serializable {

    constructor(maxLength: Int, minLength: Int) : this(maxLength, minLength, null, 0)

    constructor(maxLength: Int, minLength: Int, regexRule: String?, @StringRes regexWarn: Int) {
        check(!(maxLength < minLength || minLength < 0)) { "maxLength < minLength or minLength < 0" }
        this.maxLength = maxLength
        this.minLength = minLength
        this.regexRule = regexRule
        this.regexWarn = regexWarn
    }

    ///////////////////////////////////////////////////////////////////

    var minLength: Int//  最少输入字符数
    var maxLength: Int // 最多输入字符数
    var regexRule: String? // 正则表达式校验
    var regexWarn: Int// 正则表达式失败提示
}

