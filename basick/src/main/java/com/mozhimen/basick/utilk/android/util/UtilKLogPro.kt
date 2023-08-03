package com.mozhimen.basick.utilk.android.util

import android.util.Log
import android.view.MotionEvent
import com.mozhimen.basick.utilk.android.view.UtilKGesture
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority
import com.mozhimen.basick.elemk.cons.CCons
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKStackTrace
import com.mozhimen.basick.utilk.kotlin.collections.UtilKList
import com.mozhimen.basick.utilk.kotlin.collections.UtilKMap
import com.mozhimen.basick.utilk.kotlin.throwable2str
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName UtilKLogLong
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 13:53
 * @Version 1.0
 */
object UtilKLogPro : BaseUtilK() {

    private val _isOpenLog = AtomicBoolean(false)
    private val _isSupportLongLog = AtomicBoolean(true)

    @JvmStatic
    fun applySupportLongLog(isSupportLongLog: Boolean) {
        _isSupportLongLog.set(isSupportLongLog)
    }

    @JvmStatic
    fun applyOpenLog(isOpenLog: Boolean) {
        _isOpenLog.set(isOpenLog)
    }

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isSupportLongLog(): Boolean =
        _isSupportLongLog.get()

    @JvmStatic
    fun isOpenLog(): Boolean =
        _isOpenLog.get()

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    private fun getLogCat(vararg content: Any): String =
            UtilKStackTrace.getStackTraceInfo(parseContents(*content))

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun v(vararg content: Any) {
        vt(TAG, *content)
    }

    @JvmStatic
    fun vt(tag: String, vararg content: Any) {
        log(CLogPriority.V, tag, *content)
    }

    @JvmStatic
    fun d(vararg content: Any) {
        dt(TAG, *content)
    }

    @JvmStatic
    fun dt(tag: String, vararg content: Any) {
        log(CLogPriority.D, tag, *content)
    }

    @JvmStatic
    fun i(vararg content: Any) {
        it(TAG, content)
    }

    @JvmStatic
    fun it(tag: String, vararg content: Any) {
        log(CLogPriority.I, tag, *content)
    }

    @JvmStatic
    fun w(vararg content: Any) {
        wt(TAG, *content)
    }

    @JvmStatic
    fun wt(tag: String, vararg content: Any) {
        log(CLogPriority.W, tag, *content)
    }

    @JvmStatic
    fun e(vararg content: Any) {
        et(TAG, *content)
    }

    @JvmStatic
    fun et(tag: String, vararg content: Any) {
        log(CLogPriority.E, tag, *content)
    }

    @JvmStatic
    private fun log(type: Int, tag: String, vararg content: Any) {
        if (!isOpenLog()) return
        if (isSupportLongLog()) {
            try {
                var logCat = getLogCat(*content)
                val length = logCat.length.toLong()
                if (length <= CCons.UTILK_LOG_PRO_MAX_LOG_MSG_LENGTH) {
                    log(type, tag, logCat)
                } else {
                    while (logCat.length > CCons.UTILK_LOG_PRO_MAX_LOG_MSG_LENGTH) {
                        val logContent = logCat.substring(0, CCons.UTILK_LOG_PRO_MAX_LOG_MSG_LENGTH)
                        logCat = logCat.replace(logContent, "")
                        log(type, tag, logCat)
                    }
                    log(type, tag, logCat)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        } else {
            log(type, tag, getLogCat(*content))
        }
    }

    @JvmStatic
    private fun log(type: Int, tag: String, logCat: String) {
        if (!isOpenLog()) return
        when (type) {
            CLogPriority.V -> Log.v(tag, logCat)
            CLogPriority.D -> Log.d(tag, logCat)
            CLogPriority.I -> Log.i(tag, logCat)
            CLogPriority.W -> Log.w(tag, logCat)
            CLogPriority.E -> Log.e(tag, logCat)
            else -> Log.i(tag, logCat)
        }
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
            is Throwable -> obj.throwable2str()
            is List<*> -> UtilKList.list2str(obj)
            is Map<*, *> -> UtilKMap.map2str(obj)
            is MotionEvent -> UtilKGesture.motionEvent2str(obj.action)
            else -> obj.toString()
        }
    }
}