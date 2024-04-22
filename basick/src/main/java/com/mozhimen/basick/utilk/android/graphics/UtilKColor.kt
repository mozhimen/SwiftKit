package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.graphics.cons.CColor
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.kotlin.ranges.constraint
import kotlin.math.roundToInt

/**
 * @ClassName UtilKColor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/9 15:05
 * @Version 1.0
 */
@ColorInt
fun Int.applyColorContrast(): Int =
    UtilKColor.applyColorContrast(this)

@ColorInt
@RequiresApi(CVersCode.V_26_8_O)
fun Int.applyColorMedian(@ColorInt intColorStart: Int, @ColorInt intColorEnd: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int =
    UtilKColor.applyColorMedian(intColorStart, intColorEnd, ratio)

@ColorInt
fun Int.applyColorAlpha(factor: Float): Int =
    UtilKColor.applyColorAlpha(this, factor)

/////////////////////////////////////////////////////////////////

object UtilKColor {

    @JvmStatic
    @ColorInt
    @RequiresApi(CVersCode.V_26_8_O)
    fun argb(alpha: Float, red: Float, green: Float, blue: Float): Int =
        Color.argb(alpha, red, green, blue)

    @JvmStatic
    fun argb(alpha: Int, red: Int, green: Int, blue: Int): Int =
        Color.argb(alpha, red, green, blue)

    @JvmStatic
    fun argb(alpha: Int, @ColorInt intColor: Int): Int =
        argb(alpha, red(intColor), green(intColor), blue(intColor))

    @JvmStatic
    @ColorInt
    fun red(@ColorInt intColor: Int): Int =
        Color.red(intColor)

    @JvmStatic
    @ColorInt
    fun green(@ColorInt intColor: Int): Int =
        Color.green(intColor)

    @JvmStatic
    @ColorInt
    fun blue(@ColorInt intColor: Int): Int =
        Color.blue(intColor)

    @JvmStatic
    @ColorInt
    fun alpha(@ColorInt intColor: Int): Int =
        Color.alpha(intColor)

    /////////////////////////////////////////////////////////////////

    //颜色取反
    @JvmStatic
    fun applyColorContrast(@ColorInt intColor: Int): Int {
        val y = (299 * red(intColor) + 587 * green(intColor) + 114 * blue(intColor)) / 1000
        return if (y >= 149 && intColor != CColor.BLACK) 0xFF333333.toInt() else CColor.WHITE
    }

    /**
     * 渐变色值
     * @param intColorStart Int 开始颜色
     * @param intColorEnd Int 结束颜色
     */
    @JvmStatic
    @ColorInt
    @RequiresApi(CVersCode.V_26_8_O)
    fun applyColorMedian(@ColorInt intColorStart: Int, @ColorInt intColorEnd: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int {
        val startRed = red(intColorStart)
        val startBlue = blue(intColorStart)
        val startGreen = green(intColorStart)
        val startAlpha = alpha(intColorStart)

        val disRed = red(intColorEnd) - startRed
        val disBlue = blue(intColorEnd) - startBlue
        val disGreen = green(intColorEnd) - startGreen
        val disAlpha = alpha(intColorEnd) - startAlpha

        val medRed = startRed + ratio * disRed
        val medBlue = startBlue + ratio * disBlue
        val medGreen = startGreen + ratio * disGreen
        val medAlpha = startAlpha + ratio * disAlpha

        return argb(medAlpha, medRed, medGreen, medBlue)
    }

    /**
     * @param ratio Float 比例 0-1
     */
    @JvmStatic
    @ColorInt
    fun applyColorAlpha(@ColorInt intColor: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int =
        argb((alpha(intColor) * ratio).roundToInt().constraint(0, 1), intColor)
}