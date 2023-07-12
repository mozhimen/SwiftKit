package com.mozhimen.uicorek.adaptk.systembar.annors

/**
 * @ClassName StatusBarAnnor
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AAdaptKSystemBarPropertyAnd(
    @APropertyFilterAnd
    vararg val propertyAnd: Int,
)
