package com.mozhimen.debugk.annors

/**
 * @ClassName DebugK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 16:27
 * @Version 1.0
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ADebugKTool(val title: String, val desc: String)
