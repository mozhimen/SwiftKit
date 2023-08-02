package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.util.stream.Collectors


/**
 * @ClassName UtilKString
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/29 21:43
 * @Version 1.0
 */
fun String.getSplitLast(splitStr: String): String =
        UtilKString.getSplitLast(this, splitStr)

fun String.getSplitFirst(splitStr: String): String =
        UtilKString.getSplitFirst(this, splitStr)

fun String.getFilenameExtension(): String =
        UtilKString.getFilenameExtension(this)

fun String.hasSpace(): Boolean =
        UtilKString.hasSpace(this)

fun String.appendStrLineBreak(): String =
        UtilKString.appendStrLineBreak(this)

fun String.isNotEmptyOrElse(isNotEmptyBlock: I_Listener, orElseBlock: I_Listener) {
    UtilKString.isNotEmptyOrElse(this, isNotEmptyBlock, orElseBlock)
}

fun String.throwIllegalStateException() {
    UtilKString.throwIllegalStateException(this)
}

object UtilKString : BaseUtilK() {
    @JvmStatic
    fun appendStrLineBreak(str: String): String =
            if (!str.endsWith(CMsg.LINE_BREAK)) str + CMsg.LINE_BREAK else str

    @JvmStatic
    fun isNotEmptyOrElse(str: String, isNotEmptyBlock: I_Listener, orElseBlock: I_Listener) {
        if (str.isNotEmpty()) isNotEmptyBlock.invoke() else orElseBlock.invoke()
    }

    /**
     * 判断是否不为Empty
     * @param str Array<out String>
     * @return Boolean
     */
    @JvmStatic
    fun isNotEmpty(vararg str: String): Boolean {
        for (char in str) if (char.isEmpty()) return false
        return true
    }

    /**
     * 是否含有空格
     * @param str String
     * @return Boolean
     */
    @JvmStatic
    fun hasSpace(str: String): Boolean {
        var i = 0
        val len = str.length
        while (i < len) {
            if (!Character.isWhitespace(str[i])) return false
            ++i
        }
        return true
    }

    /**
     * 包含String
     * @param strContent String
     * @param str String
     * @return Boolean
     */
    @JvmStatic
    fun containStr(strContent: String, str: String): Boolean {
        if (strContent.isEmpty() || str.isEmpty()) return false
        return strContent.contains(str)
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 找到第一个匹配的字符的位置
     * @param strContent String
     * @param char Char
     * @return Int
     */
    @JvmStatic
    fun findFirst(strContent: String, char: Char): Int =
            strContent.indexOfFirst { it == char }

    /**
     * 找到第一个匹配的字符串的位置
     * @param strContent String
     * @param str String
     * @return Int
     */
    fun findFirst(strContent: String, str: String): Int =
            strContent.indexOf(str)

    /**
     * 切割字符串
     * @param strContent String
     * @param firstIndex Int
     * @param length Int
     * @return String
     */
    @JvmStatic
    fun subStr(strContent: String, firstIndex: Int, length: Int): String =
            strContent.substring(firstIndex.normalize(strContent.indices), if (firstIndex + length > strContent.length) strContent.length else firstIndex + length)

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 获取分割后的最后一个元素
     * @param str String
     * @param splitStr String
     * @return String
     */
    @JvmStatic
    fun getSplitLast(str: String, splitStr: String): String =
            str.substring(str.lastIndexOf(splitStr) + 1, str.length)

    /**
     * 获取分割后的第一个元素
     * @param str String
     * @param splitStr String
     * @return String
     */
    @JvmStatic
    fun getSplitFirst(str: String, splitStr: String): String =
            str.substring(0, str.indexOf(splitStr))

    /**
     * 获取扩展名
     * @param str String
     * @return String
     */
    @JvmStatic
    fun getFilenameExtension(str: String): String =
            str.substring(str.lastIndexOf(".") + 1)

    /**
     * 电话号码隐藏中间四位
     * @param str String
     * @return String
     */
    @JvmStatic
    fun hidePhone(str: String): String =
            if (str.length == 11) str.substring(0, 3) + "****" + str.substring(7, str.length) else str

    /**
     * 名字脱敏
     * @param str String
     * @return String
     */
    @JvmStatic
    fun hideName(str: String): String {
        if (str.isEmpty()) return ""
        if (str.length <= 2) return str
        val stringBuilder = StringBuilder()
        repeat(str.length - 2) {
            stringBuilder.append("*")
        }
        return str.first() + stringBuilder.toString() + str.last()
    }

    @JvmStatic
    fun throwIllegalStateException(str: String) {
        throw IllegalStateException(str)
    }
}