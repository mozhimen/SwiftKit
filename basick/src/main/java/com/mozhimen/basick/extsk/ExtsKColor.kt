package com.mozhimen.basick.extsk

import com.mozhimen.basick.utilk.UtilKColor

/**
 * @ClassName ExtsKColor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/7 12:35
 * @Version 1.0
 */
fun Any.asColorTone(): Int = UtilKColor.getColorTone(this)