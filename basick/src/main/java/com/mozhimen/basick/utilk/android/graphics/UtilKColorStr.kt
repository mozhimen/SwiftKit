package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * @ClassName UtilKColorStr
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 16:01
 * @Version 1.0
 */
fun String.asColorInt(): Int =
    UtilKColorStr.colorStr2colorInt(this)

object UtilKColorStr {
    /**
     * 获取颜色
     * @param colorStr String
     * @return Int
     */
    @JvmStatic
    @ColorInt
    fun colorStr2colorInt(colorStr: String): Int =
        Color.parseColor(colorStr)
}