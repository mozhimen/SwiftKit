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
    fun getProperty_ofLineSeparator(): String? =
        UtilKSystem.getProperty(CStrPackage.line_separator)
}