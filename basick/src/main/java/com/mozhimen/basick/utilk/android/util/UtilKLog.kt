package com.mozhimen.basick.utilk.android.util

import android.util.Log
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

object UtilKLog : BaseUtilK() {

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
    fun println(level: Int, tag: String, msg: String) {
        Log.println(level, tag, msg)
    }
}