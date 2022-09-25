package com.mozhimen.componentk.netk.mos

/**
 * @ClassName NetKCommon
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/17 13:36
 * @Version 1.0
 */
data class NetKCommon<T>(
    val code: Int,
    val msg: String?,
    val data: T
)
