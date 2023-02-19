package com.mozhimen.underlayk.logk.commons

import com.mozhimen.underlayk.logk.bases.BaseLogKConfig

/**
 * @ClassName ILogKPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:35
 * @Version 1.0
 */
interface ILogKPrinter {
    val TAG: String
        get() = "${this.javaClass.simpleName}>>>>>"

    fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String)

    fun getName(): String =
        TAG.replace(">>>>>", "")
}