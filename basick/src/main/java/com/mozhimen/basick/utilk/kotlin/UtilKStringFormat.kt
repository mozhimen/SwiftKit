package com.mozhimen.basick.utilk.kotlin

import java.nio.charset.Charset

/**
 * @ClassName UtilKStringFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:31
 * @Version 1.0
 */
fun String.str2unicode(): String =
    UtilKStringFormat.str2unicode(this)

fun String.str2bytes(charset: Charset = Charsets.UTF_8): ByteArray =
    UtilKStringFormat.str2bytes(this, charset)

object UtilKStringFormat {
    fun str2bytes(str: String, charset: Charset = Charsets.UTF_8): ByteArray =
        str.toByteArray(charset)

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
}