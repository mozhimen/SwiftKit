package com.mozhimen.basick.mok

import java.io.Serializable

/**
 * @ClassName MoKKey
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/20 17:19
 * @Version 1.0
 */
data class MoKKey(
    val id: String,
    val key: String
) : Serializable