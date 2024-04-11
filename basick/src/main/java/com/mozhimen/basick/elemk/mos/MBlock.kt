package com.mozhimen.basick.elemk.mos

import com.mozhimen.basick.elemk.commons.I_Listener


/**
 * @ClassName MBlock
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
data class MBlock(
    val name: String,
    val block: I_Listener,
    val intResAny: Int = 0
)