package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.cons.CMsg

/**
 * @ClassName UtilKStringFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:31
 * @Version 1.0
 */
fun String.asUnicode(): String =
    UtilKStringFormat.str2unicode(this)

fun Any.asStringTrim(): String =
    UtilKStringFormat.asStringTrim(this)

fun String.regexLineBreak2str(): String =
    UtilKStringFormat.regexLineBreak2str(this)

object UtilKStringFormat {
    @JvmStatic
    fun asStringTrim(charSequence: CharSequence): String =
        charSequence.toString().trim()

    @JvmStatic
    fun asStringTrim(obj: Any): String =
        obj.toString().trim()

    /**
     * icon代码转unicode
     * @param str String
     * @return String
     */
    @JvmStatic
    fun str2unicode(str: String): String {
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

    @JvmStatic
    fun regexLineBreak2str(str: String): String =
        str.replace("\\n".toRegex(), CMsg.LINE_BREAK)
}