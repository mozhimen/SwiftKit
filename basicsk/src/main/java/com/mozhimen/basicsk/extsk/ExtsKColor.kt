package com.mozhimen.basicsk.extsk

import com.mozhimen.basicsk.utilk.UtilKColor

/**
 * @ClassName ExtsKColor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/7 12:35
 * @Version 1.0
 */
fun Any.asColorTone(): Int = UtilKColor.getColorTone(this)