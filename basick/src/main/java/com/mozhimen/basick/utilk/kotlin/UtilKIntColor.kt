package com.mozhimen.basick.utilk.kotlin

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

/**
 * @ClassName UtilKIntColor
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:13
 * @Version 1.0
 */
@ColorInt
fun Int.get_ofContrast(): Int =
    UtilKIntColor.get_ofContrast(this)

fun Int.intColor2strColor(): String =
    UtilKIntColor.intColor2strColor(this)

@ColorInt
fun Int.get_ofAlpha(factor: Float): Int =
    UtilKIntColor.get_ofAlpha(this, factor)

object UtilKIntColor {
    //颜色取反
    @JvmStatic
    @ColorInt
    fun get_ofContrast(@ColorInt intColor: Int): Int {
        val y = (299 * Color.red(intColor) + 587 * Color.green(intColor) + 114 * Color.blue(intColor)) / 1000
        return if (y >= 149 && intColor != Color.BLACK) 0xFF333333.toInt() else Color.WHITE
    }

    /**
     * @param ratio Float 比例 0-1
     */
    @JvmStatic
    @ColorInt
    fun get_ofAlpha(@ColorInt intColor: Int, ratio: Float): Int =
        Color.argb((Color.alpha(intColor) * ratio).roundToInt(), Color.red(intColor), Color.green(intColor), Color.blue(intColor))

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun intColor2strColor(@ColorInt intColor: Int): String =
        String.format("#%06X", 0xFFFFFF and intColor).uppercase()
}