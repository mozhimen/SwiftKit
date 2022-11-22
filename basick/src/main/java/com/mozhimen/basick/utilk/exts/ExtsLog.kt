package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.UtilKLog

/**
 * @ClassName ExtsKLog
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 19:15
 * @Version 1.0
 */
/**
 *
 * @receiver String
 */
fun String.v() {
    UtilKLog.v(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.vt(tag: String) {
    UtilKLog.vt(tag, this)
}

/**
 *
 * @receiver String
 */
fun String.d() {
    UtilKLog.d(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.dt(tag: String) {
    UtilKLog.dt(tag, this)
}

/**
 *
 * @receiver String
 */
fun String.i() {
    UtilKLog.i(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.it(tag: String) {
    UtilKLog.it(tag, this)
}

/**
 *
 * @receiver String
 */
fun String.w() {
    UtilKLog.w(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.wt(tag: String) {
    UtilKLog.wt(tag, this)
}

/**
 *
 * @receiver String
 */
fun String.e() {
    UtilKLog.e(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.et(tag: String) {
    UtilKLog.et(tag, this)
}