package com.mozhimen.componentk.netk.async.annors

/**
 * @ClassName Field
 * @Description @BaseUrl("https://www.mozhimen.top/")
 * fun test(@Field("province") provinceId: Int)
 * @Author mozhimen
 * @Date 2021/9/23 21:22
 * @Version 1.0
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class _Field(val value: String)