package com.mozhimen.basick.utilk.android.util

import android.util.Log
import com.mozhimen.basick.elemk.android.util.annors.ALogPriority
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority
import com.mozhimen.basick.utilk.bases.BaseUtilK

fun String.v() {
    UtilKLog.v(this)
}

fun String.vt(tag: String) {
    UtilKLog.vt(tag, this)
}

fun String.d() {
    UtilKLog.d(this)
}

fun String.dt(tag: String) {
    UtilKLog.dt(tag, this)
}

fun String.i() {
    UtilKLog.i(this)
}

fun String.it(tag: String) {
    UtilKLog.it(tag, this)
}

fun String.w() {
    UtilKLog.w(this)
}

fun String.wt(tag: String) {
    UtilKLog.wt(tag, this)
}

fun String.e() {
    UtilKLog.e(this)
}

fun String.et(tag: String) {
    UtilKLog.et(tag, this)
}

fun String.println(level: Int, tag: String) {
    UtilKLog.println(level, tag, this)
}

fun Int.intLogPriority2strSimple(): String =
    UtilKLog.intLogPriority2strSimple(this)

fun Int.intLogPriority2str(): String =
    UtilKLog.intLogPriority2str(this)

object UtilKLog : BaseUtilK() {

    @JvmStatic
    fun intLogPriority2strSimple(@ALogPriority priority: Int): String =
        when (priority) {
            2 -> "V"
            3 -> "D"
            4 -> "I"
            5 -> "W"
            6 -> "E"
            7 -> "A"
            else -> "UNKNOWN"
        }

    @JvmStatic
    fun intLogPriority2str(@ALogPriority priority: Int): String =
        when (priority) {
            2 -> "VERBOSE"
            3 -> "DEBUG"
            4 -> "INFO"
            5 -> "WARN"
            6 -> "ERROR"
            7 -> "ASSERT"
            else -> "UNKNOWN"
        }

    @JvmStatic
    fun v(msg: String) {
        Log.v(TAG, msg)
    }

    @JvmStatic
    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    @JvmStatic
    fun i(msg: String) {
        Log.i(TAG, msg)
    }

    @JvmStatic
    fun w(msg: String) {
        Log.w(TAG, msg)
    }

    @JvmStatic
    fun e(msg: String) {
        Log.e(TAG, msg)
    }

    @JvmStatic
    fun vt(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    @JvmStatic
    fun dt(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    @JvmStatic
    fun it(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    @JvmStatic
    fun wt(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    @JvmStatic
    fun et(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    @JvmStatic
    fun log(level: Int, tag: String, msg: String) {
        when (level) {
            CLogPriority.V -> vt(tag, msg)
            CLogPriority.D -> dt(tag, msg)
            CLogPriority.I -> it(tag, msg)
            CLogPriority.W -> wt(tag, msg)
            CLogPriority.E -> et(tag, msg)
            else -> it(tag, msg)
        }
    }

    @JvmStatic
    fun println(level: Int, tag: String, msg: String) {
        Log.println(level, tag, msg)
    }
}