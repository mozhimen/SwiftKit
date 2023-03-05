package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.graphics.UtilKColor

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
fun String.colorString2Int(): Int =
    UtilKColor.colorString2Int(this)