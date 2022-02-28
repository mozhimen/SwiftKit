package com.mozhimen.basicsmk.logmk.helpers

import android.util.Log
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig
import com.mozhimen.basicsmk.logmk.commons.ILogMKPrinter
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig.Companion.MAX_LEN
import java.lang.StringBuilder

/**
 * @ClassName ConsolePrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:02
 * @Version 1.0
 */
class ConsolePrinter : ILogMKPrinter {
    override fun print(config: LogMKConfig, level: Int, tag: String, printString: String) {
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
}