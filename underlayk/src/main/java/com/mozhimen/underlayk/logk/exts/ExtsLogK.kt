package com.mozhimen.underlayk.logk.exts

import com.mozhimen.underlayk.logk.LogK


/**
 * @ClassName ExtsLogK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/23 18:17
 * @Version 1.0
 */
object ExtsLogK {
    private val TAG = "ExtsLogK>>>>>"

    fun String.v() {
        LogK.v(this)
    }

    fun String.vt(tag: String) {
        LogK.vt(tag, this)
    }

    fun String.d() {
        LogK.d(this)
    }

    fun String.dt(tag: String) {
        LogK.dt(tag, this)
    }

    fun String.i() {
        LogK.i(this)
    }

    fun String.it(tag: String) {
        LogK.it(tag, this)
    }

    fun String.w() {
        LogK.w(this)
    }

    fun String.wt(tag: String) {
        LogK.wt(tag, this)
    }

    fun String.e() {
        LogK.e(this)
    }

    fun String.et(tag: String) {
        LogK.et(tag, this)
    }

    fun String.a() {
        LogK.a(this)
    }

    fun String.at(tag: String) {
        LogK.at(tag, this)
    }
}