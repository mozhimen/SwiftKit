package com.mozhimen.underlayk.logk.temps.printer

import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.println
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.bases.BaseLogKPrinter
import com.mozhimen.underlayk.logk.cons.CLogKCons

/**
 * @ClassName ConsolePrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:02
 * @Version 1.0
 */
class LogKPrinterConsole(private val _ignoreLineBreak: Boolean = false) : BaseLogKPrinter() {

    override fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String) {
        super.print(config, priority, tag, msg)

        if (_ignoreLineBreak) {
            val len = msg.length
            val countOfSub = len / CLogKCons.LOG_MAX_LEN
            if (countOfSub > 0) {
                var index = 0
                for (i in 0 until countOfSub) {
                    printlog(priority, tag, msg.substring(index, index + CLogKCons.LOG_MAX_LEN))
                    index += CLogKCons.LOG_MAX_LEN
                }
                if (index != len) printlog(priority, tag, msg.substring(index, len))
            } else printlog(priority, tag, msg)
        } else {
            val logs: List<String> = msg.split(CMsg.LINE_BREAK)
            val lines: List<String>? = logs.lastOrNull()?.replace(CMsg.BLANK_STR, CMsg.TAB_STR)?.split(CMsg.LINE_BREAK_STR)
            for (log in logs) printlog(priority, tag, log)
            if (!lines.isNullOrEmpty()) for (line in lines) printlog(priority, tag, line)
        }
    }

    private fun printlog(level: Int, tag: String, printString: String) {
        printString.println(level, tag)
    }
}