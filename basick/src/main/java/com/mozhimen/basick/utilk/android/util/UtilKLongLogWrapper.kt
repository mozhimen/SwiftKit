package com.mozhimen.basick.utilk.android.util

import com.mozhimen.basick.elemk.android.util.cons.CLog
import com.mozhimen.basick.elemk.cons.CCons
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKStackTraceElement
import com.mozhimen.basick.utilk.kotlin.obj2str
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName UtilKLogLong
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 13:53
 * @Version 1.0
 */
object UtilKLongLogWrapper : BaseUtilK() {

    private val _isOpenLog = AtomicBoolean(true)
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
    private fun getLogCat(vararg msg: Any): String =
        UtilKStackTraceElement.getStackTracesInfo(parseContents(*msg))

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun v(vararg msg: Any) {
        vt(TAG, *msg)
    }

    @JvmStatic
    fun vt(tag: String, vararg msg: Any) {
        log(CLog.VERBOSE, tag, *msg)
    }

    @JvmStatic
    fun d(vararg msg: Any) {
        dt(TAG, *msg)
    }

    @JvmStatic
    fun dt(tag: String, vararg msg: Any) {
        log(CLog.DEBUG, tag, *msg)
    }

    @JvmStatic
    fun i(vararg msg: Any) {
        it(TAG, msg)
    }

    @JvmStatic
    fun it(tag: String, vararg msg: Any) {
        log(CLog.INFO, tag, *msg)
    }

    @JvmStatic
    fun w(vararg msg: Any) {
        wt(TAG, *msg)
    }

    @JvmStatic
    fun wt(tag: String, vararg msg: Any) {
        log(CLog.WARN, tag, *msg)
    }

    @JvmStatic
    fun e(vararg msg: Any) {
        et(TAG, *msg)
    }

    @JvmStatic
    fun et(tag: String, vararg msg: Any) {
        log(CLog.ERROR, tag, *msg)
    }

    @JvmStatic
    private fun log(level: Int, tag: String, vararg msg: Any) {
        if (!isOpenLog()) return
        if (isSupportLongLog()) {
            try {
                var logCat = getLogCat(*msg)
                val length = logCat.length.toLong()
                if (length <= CCons.UTILK_LOG_PRO_MAX_LOG_MSG_LENGTH) {
                    log(level, tag, logCat)
                } else {
                    while (logCat.length > CCons.UTILK_LOG_PRO_MAX_LOG_MSG_LENGTH) {
                        val logContent = logCat.substring(0, CCons.UTILK_LOG_PRO_MAX_LOG_MSG_LENGTH)
                        logCat = logCat.replace(logContent, "")
                        log(level, tag, logCat)
                    }
                    log(level, tag, logCat)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        } else
            log(level, tag, getLogCat(*msg))
    }

    @JvmStatic
    private fun log(level: Int, tag: String, msg: String) {
        if (!isOpenLog()) return
        UtilKLogWrapper.log(level, tag, msg)
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
                    .append(obj.obj2str())
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
}