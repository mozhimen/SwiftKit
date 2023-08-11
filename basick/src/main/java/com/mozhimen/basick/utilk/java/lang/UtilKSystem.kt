package com.mozhimen.basick.utilk.java.lang

import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.elemk.cons.CStrPackage

/**
 * @ClassName UtilKSystem
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/4 11:32
 * @Version 1.0
 */
object UtilKSystem {
    @JvmStatic
    fun getProperty(key: String): String? =
        System.getProperty(key)

    @JvmStatic
    fun getPropertyLineSeparator(): String? =
        getProperty(CStrPackage.LINE_SEPARATOR)

    @JvmStatic
    fun gc() {
        System.gc()
    }
}