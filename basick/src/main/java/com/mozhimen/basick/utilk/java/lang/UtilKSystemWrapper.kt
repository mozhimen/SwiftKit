package com.mozhimen.basick.utilk.java.lang

import com.mozhimen.basick.elemk.cons.CStrPackage

/**
 * @ClassName UtilKSystemProperty
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 13:43
 * @Version 1.0
 */
object UtilKSystemWrapper {
    @JvmStatic
    fun getPropertyLineSeparator(): String? =
        UtilKSystem.getProperty(CStrPackage.LINE_SEPARATOR)
}