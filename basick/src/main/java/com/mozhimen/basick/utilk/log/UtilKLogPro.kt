package com.mozhimen.basick.utilk.log

import android.util.Log
import android.view.MotionEvent
import com.mozhimen.basick.utilk.java.datatype.UtilKCollection
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.view.gesture.UtilKGesture
import com.mozhimen.basick.utilk.java.UtilKThrowable
import com.mozhimen.basick.utilk.log.cons.CLogType
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName UtilKLogLong
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 13:53
 * @Version 1.0
 */
object UtilKLogPro {
    private const val TAG = "UtilKSmartLog>>>>>"
    private const val MAX_LOG_MSG_LENGTH = 4000//logcat最大长度为4*1024，此处取4000
    private const val LOG_LONG = true//超长Log

    private val _sLOG = AtomicBoolean(false)

    @JvmStatic
    fun setOpenLog(openLog: Boolean) {
        _sLOG.set(openLog)
    }

    @JvmStatic
    fun isOpenLog(): Boolean {
        return _sLOG.get()
    }

    @JvmStatic
    fun v(vararg content: Any) {
        vt(TAG, *content)
    }

    @JvmStatic
    fun vt(tag: String, vararg content: Any) {
        printLog(CLogType.V, tag, *content)
    }

    @JvmStatic
    fun d(vararg content: Any) {
        dt(TAG, *content)
    }

    @JvmStatic
    fun dt(tag: String, vararg content: Any) {
        printLog(CLogType.D, tag, *content)
    }

    @JvmStatic
    fun i(vararg content: Any) {
        it(TAG, content)
    }

    @JvmStatic
    fun it(tag: String, vararg content: Any) {
        printLog(CLogType.I, tag, *content)
    }

    @JvmStatic
    fun w(vararg content: Any) {
        wt(TAG, *content)
    }

    @JvmStatic
    fun wt(tag: String, vararg content: Any) {
        printLog(CLogType.W, tag, *content)
    }

    @JvmStatic
    fun e(vararg content: Any) {
        et(TAG, *content)
    }

    @JvmStatic
    fun et(tag: String, vararg content: Any) {
        printLog(CLogType.E, tag, *content)
    }

    @JvmStatic
    private fun printLog(type: Int, tag: String, vararg content: Any) {
        if (!isOpenLog()) return
        if (LOG_LONG) {
            try {
                var logCat = getLogCat(*content)
                val length = logCat.length.toLong()
                if (length <= MAX_LOG_MSG_LENGTH) {
                    printLog(type, tag, logCat)
                } else {
                    while (logCat.length > MAX_LOG_MSG_LENGTH) {
                        val logContent = logCat.substring(0, MAX_LOG_MSG_LENGTH)
                        logCat = logCat.replace(logContent, "")
                        printLog(type, tag, logCat)
                    }
                    printLog(type, tag, logCat)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        } else {
            printLog(type, tag, getLogCat(*content))
        }
    }

    @JvmStatic
    private fun printLog(type: Int, tag: String, logCat: String) {
        if (!isOpenLog()) return
        when (type) {
            CLogType.V -> Log.v(tag, logCat)
            CLogType.D -> Log.d(tag, logCat)
            CLogType.I -> Log.i(tag, logCat)
            CLogType.W -> Log.w(tag, logCat)
            CLogType.E -> Log.e(tag, logCat)
            else -> Log.i(tag, logCat)
        }
    }

    @JvmStatic
    private fun getLogCat(vararg content: Any): String {
        val result: String = parseContents(*content)
        return UtilKStackTrace.getStackTraceInfo(result)
    }

    @JvmStatic
    private fun parseContents(vararg objs: Any): String {
        val stringBuilder = StringBuilder()
        if (objs.isNotEmpty()) {
            if (objs.size > 1) {
                stringBuilder.append(" {  ")
            }
            for ((index, obj) in objs.withIndex()) {
                stringBuilder.append("params【")
                    .append(index)
                    .append("】")
                    .append(" = ")
                    .append(parseContent(obj))
                if (index < objs.size - 1) {
                    stringBuilder.append(" , ")
                }
            }
            if (objs.size > 1) {
                stringBuilder.append("  }")
            }
        }
        return stringBuilder.toString()
    }

    @JvmStatic
    private fun parseContent(obj: Any): String {
        return when (obj) {
            is String -> obj
            is Throwable -> UtilKThrowable.throwable2Str(obj)
            is List<*> -> UtilKCollection.list2Str(obj)
            is Map<*, *> -> UtilKCollection.map2Str(obj)
            is MotionEvent -> UtilKGesture.motionEvent2Str(obj.action)
            else -> obj.toString()
        }
    }
}