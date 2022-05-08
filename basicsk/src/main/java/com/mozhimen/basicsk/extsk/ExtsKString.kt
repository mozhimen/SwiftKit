package com.mozhimen.basicsk.extsk

import com.mozhimen.basicsk.utilk.UtilKString

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