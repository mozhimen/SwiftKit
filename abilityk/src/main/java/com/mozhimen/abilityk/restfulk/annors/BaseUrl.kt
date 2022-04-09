package com.mozhimen.abilityk.restfulk.annors

/**
 * @ClassName BaseUrl
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/23 21:10
 * @Version 1.0
 */
/**
 * @BaseUrl("https://www.mozhimen.top/")
 * fun test(@Field("province") provinceId: Int)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl(val value: String)