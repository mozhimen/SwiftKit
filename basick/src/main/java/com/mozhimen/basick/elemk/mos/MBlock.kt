package com.mozhimen.basick.elemk.mos


/**
 * @ClassName MBlock
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/28 17:17
 * @Version 1.0
 */
data class MBlock(
    val name: String,
    val block: () -> Unit,
    val resId: Int = 0
)