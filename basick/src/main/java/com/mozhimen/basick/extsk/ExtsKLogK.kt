package com.mozhimen.basick.extsk

import com.mozhimen.basick.logk.LogK

/**
 * @ClassName ExtsKLogK
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
    LogK.v(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.vt(tag: String) {
    LogK.vt(this, tag)
}

/**
 *
 * @receiver String
 */
fun String.d() {
    LogK.d(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.dt(tag: String) {
    LogK.dt(this, tag)
}

/**
 *
 * @receiver String
 */
fun String.i() {
    LogK.i(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.it(tag: String) {
    LogK.it(this, tag)
}

/**
 *
 * @receiver String
 */
fun String.w() {
    LogK.w(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.wt(tag: String) {
    LogK.wt(this, tag)
}

/**
 *
 * @receiver String
 */
fun String.e() {
    LogK.e(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.et(tag: String) {
    LogK.et(this, tag)
}

/**
 *
 * @receiver String
 */
fun String.a() {
    LogK.a(this)
}

/**
 *
 * @receiver String
 * @param tag String
 */
fun String.at(tag: String) {
    LogK.at(this, tag)
}