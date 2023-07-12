package com.mozhimen.basick.elemk.mos

import java.io.Serializable

/**
 * @ClassName MResultISS
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/12 14:24
 * @Version 1.0
 */
data class MResultISS(
    var result: Int,
    var successMsg: String,
    var errorMsg: String
) : Serializable