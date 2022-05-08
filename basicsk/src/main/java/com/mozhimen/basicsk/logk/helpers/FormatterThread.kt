package com.mozhimen.basicsk.logk.helpers

import com.mozhimen.basicsk.logk.commons.IFormatter

/**
 * @ClassName ThreadFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:43
 * @Version 1.0
 */
class FormatterThread : IFormatter<Thread> {
    override fun format(data: Thread): String {
        return "Thread: " + data.name
    }
}