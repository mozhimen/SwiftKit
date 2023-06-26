package com.mozhimen.underlayk.logk.temps.printer

import android.util.Log
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.println
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.bases.BaseLogKPrinter
import com.mozhimen.underlayk.logk.cons.CLogKParameter

/**
 * @ClassName ConsolePrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:02
 * @Version 1.0
 */
class LogKPrinterConsole(private val _ignoreLineBreak: Boolean = false) : BaseLogKPrinter() {

    override fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String) {
        super.print(config, level, tag, printString)

        if (_ignoreLineBreak) {
            val len = printString.length
            val countOfSub = len / CLogKParameter.LOG_MAX_LEN
            if (countOfSub > 0) {
                var index = 0
                for (i in 0 until countOfSub) {
                    printlog(level, tag, printString.substring(index, index + CLogKParameter.LOG_MAX_LEN))
                    index += CLogKParameter.LOG_MAX_LEN
                }
                if (index != len) printlog(level, tag, printString.substring(index, len))
            } else printlog(level, tag, printString)
        } else {
            val logs: List<String> = printString.split(CMsg.LINE_BREAK)
            val lines: List<String>? = logs.lastOrNull()?.replace(CMsg.BLANK_STR, CMsg.TAB_STR)?.split(CMsg.LINE_BREAK_STR)
            for (log in logs) printlog(level, tag, log)
            if (!lines.isNullOrEmpty()) for (line in lines) printlog(level, tag, line)
        }
    }

    private fun printlog(level: Int, tag: String, printString: String) {
        printString.println(level, tag)
    }
}