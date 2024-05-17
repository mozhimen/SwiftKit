package com.mozhimen.basick.elemk.mos

import java.io.Serializable

/**
 * @ClassName MResult
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
data class MResultBS(
    val isSuccess: Boolean,
    val msg: String?,
) : Serializable

data class MResultBST<T>(
    val isSuccess: Boolean,
    val msg: String?,
    val bean: T
) : Serializable

data class MResultISS(
    var result: Int,
    var successMsg: String,
    var errorMsg: String
) : Serializable

data class MResultIST<T>(
    val code: Int,
    val msg: String?,
    val bean: T
) : Serializable

data class MResultSST<T>(
    val code: String,
    val msg: String?,
    val bean: T
) : Serializable