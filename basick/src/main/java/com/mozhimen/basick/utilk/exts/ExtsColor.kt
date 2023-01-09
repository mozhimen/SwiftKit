package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.UtilKColor

/**
 * @ClassName ExtsKColor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/7 12:35
 * @Version 1.0
 */
/**
 * 获取颜色
 * @receiver Any
 * @return Int
 */
fun String.getColorTone(): Int =
    UtilKColor.getColorTone(this)