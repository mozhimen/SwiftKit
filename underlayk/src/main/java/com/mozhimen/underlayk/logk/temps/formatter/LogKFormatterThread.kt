package com.mozhimen.underlayk.logk.temps.formatter

import com.mozhimen.underlayk.logk.commons.ILogKFormatter

/**
 * @ClassName ThreadFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:43
 * @Version 1.0
 */
class LogKFormatterThread : ILogKFormatter<Thread> {
    override fun format(data: Thread): String {
        return "Thread: " + data.name
    }
}