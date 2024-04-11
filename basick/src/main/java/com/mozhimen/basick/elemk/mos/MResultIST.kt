package com.mozhimen.basick.elemk.mos

import java.io.Serializable


/**
 * @ClassName MResult
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
data class MResultIST<T>(
    val code: Int,
    val msg: String?,
    val bean: T
) : Serializable