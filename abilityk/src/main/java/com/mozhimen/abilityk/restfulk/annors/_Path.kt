package com.mozhimen.abilityk.restfulk.annors

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
annotation class _Path(val value: String)