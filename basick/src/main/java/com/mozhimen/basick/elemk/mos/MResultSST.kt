package com.mozhimen.basick.elemk.mos

import java.io.Serializable


/**
 * @ClassName MResult
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
data class MResultSST<T>(
    val code: String,
    val msg: String?,
    val bean: T
) : Serializable