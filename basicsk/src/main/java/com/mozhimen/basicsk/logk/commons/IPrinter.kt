package com.mozhimen.basicsk.logk.commons

import com.mozhimen.basicsk.logk.mos.LogKConfig

/**
 * @ClassName ILogKPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:35
 * @Version 1.0
 */
interface IPrinter {
    fun print(config: LogKConfig, level: Int, tag: String, printString: String)

    fun getName(): String
}