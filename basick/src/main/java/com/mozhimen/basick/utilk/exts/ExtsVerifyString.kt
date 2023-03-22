package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.java.datatype.regular.UtilKVerifyString

/**
 * @ClassName ExtsKVerifyUrl
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 4:29
 * @Version 1.0
 */

fun String.isNumberic(): Boolean = UtilKVerifyString.checkAllNumberic(this)

fun String.hasNumberAndAlphabet(): Boolean = UtilKVerifyString.checkAllNumberAndAlphabet(this)