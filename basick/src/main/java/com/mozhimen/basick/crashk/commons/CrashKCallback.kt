package com.mozhimen.basick.crashk.commons

/**
 * @ClassName CrashKCallback
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 10:37
 * @Version 1.0
 */
open class CrashKCallback : ICrashKListener {
    override fun onGetCrashJava(msg: String?) {}

    override fun onGetCrashNative(msg: String?) {}
}