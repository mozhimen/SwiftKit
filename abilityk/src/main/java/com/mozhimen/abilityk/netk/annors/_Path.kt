package com.mozhimen.abilityk.netk.annors

/**
 * @ClassName Path
 * @Description @Get("/cities/{province}")
 * fun test(@Path("province") provinceId: Int)
 * @Author mozhimen
 * @Date 2021/9/23 21:29
 * @Version 1.0
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME  )
annotation class _Path(val value: String)