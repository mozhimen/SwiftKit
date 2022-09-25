package com.mozhimen.componentk.netk.annors

/**
 * @ClassName _Headers
 * @Description @Headers({"connection:keep-alive","auth-token:token"})
 * fun test(@Field("province") provinceId: Int)
 * @Author mozhimen
 * @Date 2021/9/23 21:26
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class _Headers(vararg val value: String)