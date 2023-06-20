package com.mozhimen.underlayk.logk.temps.printer

import android.util.Log
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.cons.CLogKParameter

/**
 * @ClassName ConsolePrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:02
 * @Version 1.0
 */
class LogKPrinterConsole : ILogKPrinter {

    override fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String) {
        val len = printString.length
        val countOfSub = len / CLogKParameter.LOG_MAX_LEN
        if (countOfSub > 0) {
            var index = 0
            for (i in 0 until countOfSub) {
                Log.println(level, tag, printString.substring(index, index + CLogKParameter.LOG_MAX_LEN))
                index += CLogKParameter.LOG_MAX_LEN
            }
            if (index != len) {
                Log.println(level, tag, printString.substring(index, len))
            }
        } else {
            Log.println(level, tag, printString)
        }
    }
}