package com.mozhimen.basicsk.utilk

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
}