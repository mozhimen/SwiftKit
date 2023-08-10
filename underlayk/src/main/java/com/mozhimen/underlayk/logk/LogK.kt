package com.mozhimen.underlayk.logk

import com.mozhimen.underlayk.logk.commons.ILogK
import com.mozhimen.underlayk.logk.helpers.LogKProvider

/**
 * @ClassName LogK
 * @Description * Tips:
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 * <p>
 * Sample1:
 * LogK.log(object : LogKConfig() {
 * override fun includeThread(): Boolean {
 * return true
 * }
 * override fun stackTraceDepth(): Int {
 * return 0
 * }
 * }, CLogKType.E, "-----", "5566")
 * <p></>
 * Sample2:
 * LogK.a("9900")
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:49
 * @Version 1.0
 */
fun String.vk() {
    LogK.vk(this)
}

fun String.vtk(tag: String) {
    LogK.vtk(tag, this)
}

fun String.dk() {
    LogK.dk(this)
}

fun String.dtk(tag: String) {
    LogK.dtk(tag, this)
}

fun String.ik() {
    LogK.ik(this)
}

fun String.itk(tag: String) {
    LogK.itk(tag, this)
}

fun String.wk() {
    LogK.wk(this)
}

fun String.wtk(tag: String) {
    LogK.wtk(tag, this)
}

fun String.ek() {
    LogK.ek(this)
}

fun String.etk(tag: String) {
    LogK.etk(tag, this)
}

fun String.ak() {
    LogK.ak(this)
}

fun String.atk(tag: String) {
    LogK.atk(tag, this)
}

object LogK : ILogK by LogKProvider()