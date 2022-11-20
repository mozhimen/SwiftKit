package com.mozhimen.basick.elemk.mos

import java.io.Serializable

/**
 * @ClassName MoKKey
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/20 17:19
 * @Version 1.0
 */
data class MKey(
    val id: String,
    val key: String
) : Serializable