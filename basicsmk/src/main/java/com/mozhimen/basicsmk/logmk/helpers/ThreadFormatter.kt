package com.mozhimen.basicsmk.logmk.helpers

import com.mozhimen.basicsmk.logmk.commons.ILogMKFormatter

/**
 * @ClassName ThreadFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:43
 * @Version 1.0
 */
class ThreadFormatter : ILogMKFormatter<Thread> {
    override fun format(data: Thread): String {
        return "Thread: " + data.name
    }
}