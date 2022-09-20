package com.mozhimen.basick.datak

import java.io.Serializable

/**
 * @ClassName DataKKey
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/20 17:19
 * @Version 1.0
 */
data class DataKKey(
    val id: String,
    val key: String
) : Serializable