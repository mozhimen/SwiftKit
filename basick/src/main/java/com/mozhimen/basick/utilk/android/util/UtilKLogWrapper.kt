package com.mozhimen.basick.utilk.android.util

import android.util.Log
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.elemk.android.util.cons.CLog
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.util.concurrent.atomic.AtomicBoolean

fun String.v() {
    UtilKLogWrapper.v(this)
}

fun String.vt(tag: String) {
    UtilKLogWrapper.vt(tag, this)
}

fun String.d() {
    UtilKLogWrapper.d(this)
}

fun String.dt(tag: String) {
    UtilKLogWrapper.dt(tag, this)
}

fun String.i() {
    UtilKLogWrapper.i(this)
}

fun String.it(tag: String) {
    UtilKLogWrapper.it(tag, this)
}

fun String.w() {
    UtilKLogWrapper.w(this)
}

fun String.wt(tag: String) {
    UtilKLogWrapper.wt(tag, this)
}

fun String.e() {
    UtilKLogWrapper.e(this)
}

fun String.et(tag: String) {
    UtilKLogWrapper.et(tag, this)
}

object UtilKLogWrapper : BaseUtilK() {

    private val _isLogEnable = AtomicBoolean(BuildConfig.DEBUG)

    @JvmStatic
    fun setLogEnable(enable: Boolean) {
        _isLogEnable.set(enable)
    }

    @JvmStatic
    fun getLogEnable(): Boolean =
        _isLogEnable.get()

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
    fun vt(tag: String, msg: String) {
        log(CLog.VERBOSE, tag, msg)
    }

    @JvmStatic
    fun dt(tag: String, msg: String) {
        log(CLog.DEBUG, tag, msg)
    }

    @JvmStatic
    fun it(tag: String, msg: String) {
        log(CLog.INFO, tag, msg)
    }

    @JvmStatic
    fun wt(tag: String, msg: String) {
        log(CLog.WARN, tag, msg)
    }

    @JvmStatic
    fun et(tag: String, msg: String) {
        log(CLog.ERROR, tag, msg)
    }

    @JvmStatic
    fun log(level: Int, tag: String, msg: String) {
        if (!getLogEnable()) return
        Log.println(level, tag, msg)
    }

    @JvmStatic
    fun longLog(level: Int, tag: String, msg: String) {
        if (!getLogEnable()) return
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