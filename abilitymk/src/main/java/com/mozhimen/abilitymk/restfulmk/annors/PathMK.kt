package com.mozhimen.abilitymk.restfulmk.annors

/**
 * @ClassName Path
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/23 21:29
 * @Version 1.0
 */

/**
 * @Get("/cities/{province}")
 * fun test(@Path("province") provinceId: Int)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME  )
annotation class PathMK(val value: String)