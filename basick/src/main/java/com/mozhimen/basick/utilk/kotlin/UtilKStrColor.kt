package com.mozhimen.basick.utilk.kotlin

import android.graphics.Color
import androidx.annotation.ColorInt
/**
 * @ClassName UtilKStrColor
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:12
 * @Version 1.0
 */
@ColorInt
fun String.strColor2intColor(): Int =
    UtilKStrColor.strColor2intColor(this)

object UtilKStrColor {
    /**
     * 获取颜色
     * @param strColor String
     * @return Int
     */
    @JvmStatic
    @ColorInt
    fun strColor2intColor(strColor: String): Int =
        Color.parseColor(strColor)
}