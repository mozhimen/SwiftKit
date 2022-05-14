package com.mozhimen.abilityk.netk.annors.methods

/**
 * @ClassName _GET
 * @Description @Get("/cities/all")
 * fun test(@Field("province") provinceId: Int)
 * @Author mozhimen
 * @Date 2021/9/23 21:24
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class _GET(val value: String)