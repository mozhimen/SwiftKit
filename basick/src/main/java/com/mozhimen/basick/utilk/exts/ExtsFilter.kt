package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.datatype.regular.UtilKFilter


/**
 * @ClassName ExtsFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/19 11:18
 * @Version 1.0
 */
fun String.filterNumber(): String {
    return UtilKFilter.filterNumber(this)
}

fun String.filterAlphabet(): String {
    return UtilKFilter.filterAlphabet(this)
}

fun String.filterChinese(): String {
    return UtilKFilter.filterChinese(this)
}

fun String.filter(): String {
    return UtilKFilter.filter(this)
}

fun String.filterLength(endIndex: Int): String {
    return UtilKFilter.filterLength(this, endIndex)
}