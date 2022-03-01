package com.mozhimen.abilitymk.restfulmk.annors.methods

/**
 * @ClassName PUTMK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 12:22
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PUTMK(val value: String, val formPost: Boolean = false)
