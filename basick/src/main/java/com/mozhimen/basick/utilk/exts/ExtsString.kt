package com.mozhimen.basick.utilk.exts

import android.graphics.Bitmap
import com.mozhimen.basick.utilk.java.datatype.UtilKString
import java.util.*

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
 * 获取分割后的第一个元素
 * @receiver String
 * @param splitStr String
 * @return String
 */
fun String.getSplitFirst(splitStr: String): String = UtilKString.getSplitFirst(this, splitStr)

/**
 * icon代码转unicode
 * @receiver String
 * @return String
 */
fun String.str2Unicode(): String =
    UtilKString.str2Unicode(this)

/**
 * bool转String
 * @receiver Boolean
 * @param locale Locale
 * @return String
 */
fun Boolean.boolean2Str(locale: Locale = Locale.CHINA): String =
    UtilKString.boolean2Str(this, locale)

/**
 * decimal转String
 * @receiver Double
 * @param pattern String
 * @return String
 */
fun Double.decimal2Str(pattern: String = "#.0"): String =
    UtilKString.decimal2Str(this, pattern)

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
fun <T> Array<T>.joinArray2Str(defaultValue: String = "", splitChar: String = ","): String =
    UtilKString.joinArray2Str(this, defaultValue, splitChar)

/**
 * 聚合list
 * @receiver List<T>
 * @param defaultValue String
 * @param splitStr String
 * @return String
 */
fun <T> List<T>.joinList2Str(defaultValue: String = "", splitStr: String = ","): String =
    UtilKString.joinList2Str(this, defaultValue, splitStr)

fun String.getFilenameExtension(): String =
    UtilKString.getFilenameExtension(this)

fun CharSequence.toStringTrim(): String =
    UtilKString.toStringTrim(this)

fun Any.toStringTrim(): String =
    UtilKString.toStringTrim(this)