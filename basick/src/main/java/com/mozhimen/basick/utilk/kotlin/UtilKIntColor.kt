package com.mozhimen.basick.utilk.kotlin

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.graphics.UtilKColor
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

@ColorInt
fun Int.get_ofMedian(@ColorInt intColorStart: Int, @ColorInt intColorEnd: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int =
    UtilKIntColor.get_ofMedian(intColorStart, intColorEnd, ratio)

@ColorInt
fun Int.get_ofAlpha(factor: Float): Int =
    UtilKIntColor.get_ofAlpha(this, factor)

/////////////////////////////////////////////////////////////////////////

fun Int.intColor2strColor(): String =
    UtilKIntColor.intColor2strColor(this)

/////////////////////////////////////////////////////////////////////////

object UtilKIntColor {
    //颜色取反
    @JvmStatic
    @ColorInt
    fun get_ofContrast(@ColorInt intColor: Int): Int {
        val y = (299 * Color.red(intColor) + 587 * Color.green(intColor) + 114 * Color.blue(intColor)) / 1000
        return if (y >= 149 && intColor != Color.BLACK) 0xFF333333.toInt() else Color.WHITE
    }

    /**
     * 渐变色值
     * @param intColorStart Int 开始颜色
     * @param intColorEnd Int 结束颜色
     */
    @JvmStatic
    @ColorInt
    @RequiresApi(CVersCode.V_26_8_O)
    fun get_ofMedian(@ColorInt intColorStart: Int, @ColorInt intColorEnd: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int {
        val startRed = Color.red(intColorStart)
        val startBlue = Color.blue(intColorStart)
        val startGreen = Color.green(intColorStart)
        val startAlpha = Color.alpha(intColorStart)

        val disRed = Color.red(intColorEnd) - startRed
        val disBlue = Color.blue(intColorEnd) - startBlue
        val disGreen = Color.green(intColorEnd) - startGreen
        val disAlpha = Color.alpha(intColorEnd) - startAlpha

        val medRed = startRed + ratio * disRed
        val medBlue = startBlue + ratio * disBlue
        val medGreen = startGreen + ratio * disGreen
        val medAlpha = startAlpha + ratio * disAlpha

        return UtilKColor.argb(medAlpha, medRed, medGreen, medBlue)
    }

    /**
     * @param ratio Float 比例 0-1
     */
    @JvmStatic
    @ColorInt
    fun get_ofAlpha(@ColorInt intColor: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int =
        UtilKColor.argb((Color.alpha(intColor) * ratio).roundToInt(), Color.red(intColor), Color.green(intColor), Color.blue(intColor))

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun intColor2strColor(@ColorInt intColor: Int): String =
        String.format("#%06X", 0xFFFFFF and intColor).uppercase()
}