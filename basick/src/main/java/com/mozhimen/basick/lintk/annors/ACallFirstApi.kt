package com.mozhimen.basick.lintk.annors

/**
 * @ClassName OApiCall_First
 * @Description 首先调用的API
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/28
 * @Version 1.0
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.TYPE,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.EXPRESSION,
)
@Retention(AnnotationRetention.SOURCE)
annotation class ACallFirstApi