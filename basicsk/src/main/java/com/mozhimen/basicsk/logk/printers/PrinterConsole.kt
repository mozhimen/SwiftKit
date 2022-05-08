package com.mozhimen.basicsk.logk.printers

import android.util.Log
import com.mozhimen.basicsk.logk.mos.LogKConfig
import com.mozhimen.basicsk.logk.commons.IPrinter
import com.mozhimen.basicsk.logk.mos.LogKConfig.Companion.MAX_LEN

/**
 * @ClassName ConsolePrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:02
 * @Version 1.0
 */
class PrinterConsole : IPrinter {
    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        val len = printString.length
        val countOfSub = len / MAX_LEN
        if (countOfSub > 0) {
            var index = 0
            for (i in 0 until countOfSub) {
                Log.println(level, tag, printString.substring(index, index + MAX_LEN))
                index += MAX_LEN
            }
            if (index != len) {
                Log.println(level, tag, printString.substring(index, len))
            }
        } else {
            Log.println(level, tag, printString)
        }
    }

    override fun getName(): String = this.javaClass.simpleName
}