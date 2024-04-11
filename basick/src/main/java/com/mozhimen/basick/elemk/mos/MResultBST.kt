package com.mozhimen.basick.elemk.mos

import java.io.Serializable


/**
 * @ClassName MResult
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
data class MResultBST<T>(
    val isSuccess: Boolean,
    val msg: String?,
    val bean: T
) : Serializable