package com.mozhimen.debugk.annors

/**
 * @ClassName DebugKParamsAnnor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/29 18:53
 * @Version 1.0
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ADebugKParams(val title: String)
