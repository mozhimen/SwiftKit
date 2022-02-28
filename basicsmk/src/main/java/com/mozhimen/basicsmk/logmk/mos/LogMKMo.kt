package com.mozhimen.basicsmk.logmk.mos

import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName LogMKMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:46
 * @Version 1.0
 */
class LogMKMo(var timeMillis: Long, var level: Int, var tag: String, var log: String) {
    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    fun flattenedLog(): String {
        return getFlattened() + "\n" + log
    }

    fun getFlattened(): String {
        return format(timeMillis) + '|' + level + '|' + tag + "|:"
    }

    private fun format(timeMillis: Long): String {
        return sdf.format(timeMillis)
    }
}