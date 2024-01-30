package com.mozhimen.basick.lintk.annors

/**
 * @ClassName Description
 * @Description 描述
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 23:05
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class ADescription(vararg val tip: String)
