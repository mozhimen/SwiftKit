package com.mozhimen.basick.lintk.annors

import androidx.annotation.Dimension

/**
 * @ClassName Dp
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/2
 * @Version 1.0
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE
)
@Dimension(unit = Dimension.DP)
annotation class Dp
