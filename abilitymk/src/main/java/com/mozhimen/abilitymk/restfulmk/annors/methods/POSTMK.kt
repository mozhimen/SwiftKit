package com.mozhimen.abilitymk.restfulmk.annors.methods

/**
 * @ClassName Post
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/23 21:33
 * @Version 1.0
 */

/**
 * @Post("/cities/{province}")
 * fun test(@Path("province") provinceId: Int)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class POSTMK(val value: String, val isFormPost: Boolean)