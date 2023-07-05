package com.mozhimen.basick.elemk.mos


/**
 * @ClassName MResult
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/20 12:13
 * @Version 1.0
 */
data class MResultSST<B>(
    val code: String,
    val msg: String?,
    val bean: B
)