package com.mozhimen.componentk.netk.annors

/**
 * @ClassName _BaseUrl
 * @Description @BaseUrl("https://www.mozhimen.top/")
 * fun test(@Field("province") provinceId: Int)
 * @Author mozhimen
 * @Date 2021/9/23 21:10
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class _BaseUrl(val value: String)