package com.mozhimen.underlayk.crashk.commons

/**
 * @ClassName CrashKCallback
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 17:27
 * @Version 1.0
 */
interface ICrashKListener {
    fun onGetCrashJava(msg: String?)
    fun onGetCrashNative(msg: String?)
}