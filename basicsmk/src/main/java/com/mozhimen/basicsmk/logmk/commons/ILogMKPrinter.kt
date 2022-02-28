package com.mozhimen.basicsmk.logmk.commons

import com.mozhimen.basicsmk.logmk.mos.LogMKConfig

/**
 * @ClassName ILogMKPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:35
 * @Version 1.0
 */
interface ILogMKPrinter {
    fun print(config: LogMKConfig, level: Int, tag: String, printString: String)
}