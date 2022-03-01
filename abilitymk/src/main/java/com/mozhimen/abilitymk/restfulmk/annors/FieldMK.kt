package com.mozhimen.abilitymk.restfulmk.annors

/**
 * @ClassName Field
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/23 21:22
 * @Version 1.0
 */
/**
 * @BaseUrl("https://www.mozhimen.top/")
 * fun test(@Field("province") provinceId: Int)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class FieldMK(val value: String)