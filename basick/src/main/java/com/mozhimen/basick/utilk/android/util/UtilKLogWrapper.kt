package com.mozhimen.basick.utilk.android.util

import android.util.Log
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.elemk.android.util.cons.CLog
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.getStrMessage
import java.lang.Exception
import java.util.concurrent.atomic.AtomicBoolean

fun String.v() {
    UtilKLogWrapper.v(this)
}

fun String.v(tag: String) {
    UtilKLogWrapper.v(tag, this)
}

fun String.d() {
    UtilKLogWrapper.d(this)
}

fun String.d(tag: String) {
    UtilKLogWrapper.d(tag, this)
}

fun String.i() {
    UtilKLogWrapper.i(this)
}

fun String.i(tag: String) {
    UtilKLogWrapper.i(tag, this)
}

fun String.w() {
    UtilKLogWrapper.w(this)
}

fun String.w(tag: String) {
    UtilKLogWrapper.w(tag, this)
}

fun String.e() {
    UtilKLogWrapper.e(this)
}

fun String.e(tag: String) {
    UtilKLogWrapper.e(tag, this)
}

///////////////////////////////////////////////////////////////////////////

fun String.log(level: Int, tag: String) {
    UtilKLogWrapper.log(level, tag, this)
}

///////////////////////////////////////////////////////////////////////////

object UtilKLogWrapper : BaseUtilK() {

    private val _isLogEnable = AtomicBoolean(BuildConfig.DEBUG)

    @JvmStatic
    fun isLogEnable(enable: Boolean) {
        _isLogEnable.set(enable)
    }

    @JvmStatic
    fun isLogEnable(): Boolean =
        _isLogEnable.get()

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun v(msg: String) {
        log(CLog.VERBOSE, TAG, msg)
    }

    @JvmStatic
    fun d(msg: String) {
        log(CLog.DEBUG, TAG, msg)
    }

    @JvmStatic
    fun i(msg: String) {
        log(CLog.INFO, TAG, msg)
    }

    @JvmStatic
    fun w(msg: String) {
        log(CLog.WARN, TAG, msg)
    }

    @JvmStatic
    fun e(msg: String) {
        log(CLog.ERROR, TAG, msg)
    }

    @JvmStatic
    fun e(exception: Exception) {
        log(CLog.ERROR, TAG, exception.message ?: "")
    }

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun v(tag: String, msg: String) {
        log(CLog.VERBOSE, tag, msg)
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        log(CLog.DEBUG, tag, msg)
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        log(CLog.INFO, tag, msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        log(CLog.WARN, tag, msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        log(CLog.ERROR, tag, msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String, exception: Exception) {
        log(CLog.ERROR, tag, msg + " " + exception.getStrMessage())
    }

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun log(level: Int, tag: String, msg: String) {
        if (!isLogEnable()) return
        Log.println(level, tag, msg)
    }

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun longLog(level: Int, tag: String, msg: String) {
        if (!isLogEnable()) return
        val segmentSize = 1024
        val logLength = msg.length
        if (logLength < segmentSize) {
            log(level, tag, msg)
        } else {
            var startIndex = 0
            while (startIndex < logLength) {
                var endIndex = startIndex + segmentSize
                if (endIndex > logLength) {
                    endIndex = logLength
                }
                val printMessage = msg.subSequence(startIndex, endIndex)
                log(level, tag, printMessage.toString())
                startIndex = endIndex
            }
        }
    }
}