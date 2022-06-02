package com.mozhimen.basick.extsk

import com.bumptech.glide.util.Util
import com.mozhimen.basick.utilk.UtilKString
import java.util.*
import java.util.regex.Pattern

/**
 * @ClassName ExtsKString
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/30 23:59
 * @Version 1.0
 */
/**
 * 获取分割后的最后一个元素
 * @receiver String
 * @param splitStr String
 * @return String
 */
fun String.getSplitLast(splitStr: String): String = UtilKString.getSplitLast(this, splitStr)

/**
 * icon代码转unicode
 * @receiver String
 * @return String
 */
fun String.string2Unicode(): String = UtilKString.string2Unicode(this)

/**
 * bool转String
 * @receiver Boolean
 * @param locale Locale
 * @return String
 */
fun Boolean.boolean2String(locale: Locale = Locale.CHINA): String = UtilKString.boolean2String(this, locale)

/**
 * decimal转String
 * @receiver Double
 * @param pattern String
 * @return String
 */
fun Double.decimal2String(pattern: String = "#.0"): String = UtilKString.decimal2String(this, pattern)

/**
 * 抛出非法状态异常
 * @receiver String
 */
fun String.throwIllegalStateException() {
    throw IllegalStateException(this)
}

/**
 * 聚合array
 * @receiver Array<T>
 * @param defaultValue String
 * @param splitChar String
 * @return String
 */
fun <T> Array<T>.joinArray(defaultValue: String = "", splitChar: String = ","): String = UtilKString.joinArray(this, defaultValue, splitChar)

/**
 * 聚合list
 * @receiver List<T>
 * @param defaultValue String
 * @param splitStr String
 * @return String
 */
fun <T> List<T>.joinList(defaultValue: String = "", splitStr: String = ","): String = UtilKString.joinList(this, defaultValue, splitStr)