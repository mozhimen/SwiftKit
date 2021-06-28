package com.mozhimen.swiftmk.utils.log

import android.util.Log

/**
 * @ClassName LogUtil
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/23 16:06
 * @Version 1.0
 */
object LogUtil {
    private const val VERBOSE = 1

    private const val DEBUG = 2

    private const val INFO = 3

    private const val WARN = 4

    private const val ERROR = 5

    var level = VERBOSE

    fun v(tag: String, msg: String) {
        if (level <= VERBOSE) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (level <= DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (level <= INFO) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (level <= WARN) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (level < ERROR) {
            Log.e(tag, msg)
        }
    }
}