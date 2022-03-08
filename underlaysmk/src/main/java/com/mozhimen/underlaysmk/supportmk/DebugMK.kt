package com.mozhimen.underlaysmk.supportmk

/**
 * @ClassName DebugMK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 16:27
 * @Version 1.0
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DebugMK(val name: String, val desc: String)
