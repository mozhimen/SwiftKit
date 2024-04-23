package com.mozhimen.basick.utilk.java.lang

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import java.util.logging.Logger

/**
 * @ClassName StackTraceUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:42
 * @Version 1.0
 */
object UtilKStackTraceElement : IUtilK {

    @JvmStatic
    fun get(clazz: Class<*>): StackTraceElement? {
        val stackTrace = UtilKThread.getStackTrace_ofCur()
        var stackOffset = getOffset(stackTrace, clazz)
        if (stackOffset == -1) {
            stackOffset = getOffset(stackTrace, Logger::class.java)
            if (stackOffset == -1) {
                stackOffset = getOffset(stackTrace, Log::class.java)
                if (stackOffset == -1)
                    return null
            }
        }
        return stackTrace[stackOffset]
    }

    @JvmStatic
    fun getOffset(stackTraceElements: Array<StackTraceElement>, clazz: Class<*>): Int {
        var logIndex = -1
        for (i in stackTraceElements.indices) {
            val element = stackTraceElements[i]
            val tClass = element.className
            if (TextUtils.equals(tClass, clazz.name))
                logIndex = i
            else
                if (logIndex > -1) break
        }
        if (logIndex != -1) {
            logIndex++
            if (logIndex >= stackTraceElements.size)
                logIndex = stackTraceElements.size - 1
        }
        return logIndex
    }
}