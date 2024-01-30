package com.mozhimen.basick.lintk.annors

import androidx.annotation.IntRange

/**
 * @ClassName Description
 * @Description 描述
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 23:05
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
annotation class AUsableApi(@IntRange(from = 1) val api: Int)
