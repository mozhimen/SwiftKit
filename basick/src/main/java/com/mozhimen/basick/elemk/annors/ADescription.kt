package com.mozhimen.basick.elemk.annors

/**
 * @ClassName Description
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 23:05
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ADescription(val tip: String)
