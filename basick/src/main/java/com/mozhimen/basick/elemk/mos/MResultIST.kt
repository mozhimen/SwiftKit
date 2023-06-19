package com.mozhimen.basick.elemk.mos


/**
 * @ClassName MResult
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/20 12:13
 * @Version 1.0
 */
data class MResultIST<T>(
    val code: Int,
    val msg: String?,
    val root: T
)