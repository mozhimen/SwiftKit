package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.java.datatype.regular.UtilKFilter


/**
 * @ClassName ExtsFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/19 11:18
 * @Version 1.0
 */
fun String.outNumber(): String =
    UtilKFilter.outNumber(this)

fun String.outAlphabet(): String =
    UtilKFilter.outAlphabet(this)

fun String.outChinese(): String =
    UtilKFilter.outChinese(this)

fun String.outNAC(): String =
    UtilKFilter.outNAC(this)

fun String.outLength(endIndex: Int): String =
    UtilKFilter.outLength(this, endIndex)