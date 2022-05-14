package com.mozhimen.abilityk.netk.annors.methods

/**
 * @ClassName PUTK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 12:22
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class _PUT(val value: String, val formPost: Boolean = false)
