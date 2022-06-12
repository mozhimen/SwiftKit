package com.mozhimen.basick.utilk

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.util.UUIDUtil
import java.text.DecimalFormat
import java.util.*
import java.util.stream.Collectors

/**
 * @ClassName UtilKString
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/29 21:43
 * @Version 1.0
 */
object UtilKString {
    /**
     * 获取分割后的最后一个元素
     * @param str String
     * @param splitStr String
     * @return String
     */
    fun getSplitLast(str: String, splitStr: String): String = str.substring(str.lastIndexOf(splitStr) + 1, str.length)

    /**
     * icon代码转unicode
     * @param str String
     * @return String
     */
    fun string2Unicode(str: String): String {
        if (str.isEmpty()) return ""
        val stringBuffer = StringBuffer()
        if (str.startsWith("&#x")) {
            val hex = str.replace("&#x", "").replace(";", "").trim()
            val data = Integer.parseInt(hex, 16)
            stringBuffer.append(data.toChar())
        } else if (str.startsWith("&#")) {
            val hex = str.replace("&#", "").replace(";", "").trim()
            val data = Integer.parseInt(hex, 10)
            stringBuffer.append(data.toChar())
        } else if (str.startsWith("\\u")) {
            val hex = str.replace("\\u", "").trim()
            val data = Integer.parseInt(hex, 16)
            stringBuffer.append(data.toChar())
        } else {
            return str
        }

        return stringBuffer.toString()
    }

    /**
     * decimal转String
     * @param double Double
     * @param pattern String
     * @return String
     */
    fun decimal2String(double: Double, pattern: String = "#.0"): String = DecimalFormat(pattern).format(double)

    /**
     * bool转String
     * @param bool Boolean
     * @param locale Locale
     * @return String
     */
    fun boolean2String(bool: Boolean, locale: Locale = Locale.CHINA) =
        if (locale == Locale.CHINA) if (bool) "是" else "否" else (if (bool) "true" else "false")

    /**
     * 聚合array
     * @param array Array<T>
     * @param defaultValue String
     * @param splitChar String
     * @return String
     */

    fun <T> joinArray(array: Array<T>, defaultValue: String = "", splitChar: String = ","): String =
        joinList(array.toList(), defaultValue, splitChar)

    /**
     * 聚合list
     * @param list List<T>
     * @param defaultValue String
     * @param splitChar String
     * @return String
     */
    fun <T> joinList(list: List<T>, defaultValue: String = "", splitChar: String = ","): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val ret = list.stream().map { elem: T? -> elem?.toString() ?: "" }
                .collect(Collectors.joining(splitChar))
            ret.ifEmpty { defaultValue }
        } else {
            val stringBuilder = StringBuilder()
            list.forEach {
                stringBuilder.append(it?.toString() ?: "").append(splitChar)
            }
            if (stringBuilder.isNotEmpty()) stringBuilder.deleteAt(stringBuilder.length - 1).toString() else defaultValue
        }

    /**
     * 电话号码隐藏中间四位
     * @param str String
     * @return String
     */
    fun hidePhone(str: String): String = if (str.length == 11) str.substring(0, 3) + "****" + str.substring(7, str.length) else str

    /**
     * 名字脱敏
     * @param str String
     * @return String
     */
    fun hideName(str: String): String {
        if (str.isEmpty()) return ""
        if (str.length <= 2) return str
        val stringBuilder = StringBuilder()
        repeat(str.length - 2) {
            stringBuilder.append("*")
        }
        return str.first() + stringBuilder.toString() + str.last()
    }

    /**
     * 生成随机字符串
     * @return String
     */
    fun getRandomUuid(): String =
        UUID.randomUUID().toString().replace("-", "")
}