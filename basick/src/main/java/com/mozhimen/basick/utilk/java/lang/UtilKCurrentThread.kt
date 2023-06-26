package com.mozhimen.basick.utilk.java.lang

/**
 * @ClassName UtilKThread
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/25 0:03
 * @Version 1.0
 */
object UtilKCurrentThread {
    @JvmStatic
    fun get(): Thread =
        Thread.currentThread()

    @JvmStatic
    fun getName(): String =
        get().name

    @JvmStatic
    fun getStackTrace(): Array<StackTraceElement> =
        get().stackTrace
}