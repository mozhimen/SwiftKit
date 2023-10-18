package com.mozhimen.basick.utilk.kotlin

import androidx.annotation.ColorInt
import com.mozhimen.basick.utilk.android.graphics.UtilKColor

/**
 * @ClassName UtilKIntColor
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:13
 * @Version 1.0
 */
fun Int.intColor2strColor(): String =
    UtilKIntColor.intColor2strColor(this)

object UtilKIntColor {
    @JvmStatic
    fun intColor2strColor(@ColorInt colorInt: Int): String =
        String.format("#%06X", 0xFFFFFF and colorInt).uppercase()
}