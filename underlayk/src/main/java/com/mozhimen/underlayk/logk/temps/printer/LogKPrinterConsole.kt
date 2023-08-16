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
            } else
                printlog(priority, tag, msg)
        } else {
            val msgs: MutableSet<String> = msg.split(CMsg.LINE_BREAK).toMutableSet()
            val lines: List<String>? = msgs.lastOrNull()?.replace(CMsg.BLANK_STR, CMsg.TAB_STR)?.split(CMsg.LINE_BREAK_STR)
            if (!lines.isNullOrEmpty())
                msgs.addAll(msgs)
            for (m in msgs)
                printlog(priority, tag, m)
        }

        (getName() + CMsg.PART_LINE_HOR).println(priority, tag)
    }

    private fun printlog(level: Int, tag: String, msg: String) {
        msg.println(level, tag)
    }
}