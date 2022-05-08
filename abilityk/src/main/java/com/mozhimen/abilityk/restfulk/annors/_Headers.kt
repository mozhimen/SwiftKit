package com.mozhimen.abilityk.restfulk.annors

/**
 * @ClassName Headers
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/23 21:26
 * @Version 1.0
 */

/**
 * @Headers({"connection:keep-alive","auth-token:token"})
 * fun test(@Field("province") provinceId: Int)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class _Headers(vararg val value: String)