package com.mozhimen.basick.sensek.systembar.annors

/**
 * @ClassName StatusBarKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:41
 * @Version 1.0
 */
internal annotation class ASenseKSystemBarMultiProperty(
    @ASenseKSystemBarType
    vararg val systemBarType: Int
)