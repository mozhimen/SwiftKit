package com.mozhimen.basick.utilk

import android.util.Log

object UtilKLog {
    private const val TAG = "UtilKLog>>>>>"

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
}