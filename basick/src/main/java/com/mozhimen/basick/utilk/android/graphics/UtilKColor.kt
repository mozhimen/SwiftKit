package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import kotlin.math.roundToInt

/**
 * @ClassName UtilKColor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/9 15:05
 * @Version 1.0
 */
@ColorInt
fun Int.applyAdjustAlpha(factor: Float): Int =
    UtilKColor.applyAdjustAlpha(this, factor)

@ColorInt
fun Int.getContrastColor(): Int =
    UtilKColor.getContrastColor(this)

fun Int.colorInt2colorStr(): String =
    UtilKColor.colorInt2colorStr(this)

@ColorInt
fun String.colorStr2colorInt(): Int =
    UtilKColor.colorStr2colorInt(this)

object UtilKColor {
    @JvmStatic
    @ColorInt
    fun getContrastColor(@ColorInt colorInt: Int): Int {
        val y = (299 * Color.red(colorInt) + 587 * Color.green(colorInt) + 114 * Color.blue(colorInt)) / 1000
        return if (y >= 149 && colorInt != Color.BLACK) 0xFF333333.toInt() else Color.WHITE
    }

    /**
     * 渐变色值
     * @param startColorInt Int 开始颜色
     * @param endColorInt Int 结束颜色
     * @param ratio Float
     * @return Int
     */
    @JvmStatic
    @ColorInt
    @RequiresApi(CVersCode.V_26_8_O)
    fun getMedianColor(@ColorInt startColorInt: Int, @ColorInt endColorInt: Int, ratio: Float): Int {
        val startRed = Color.red(startColorInt)
        val startBlue = Color.blue(startColorInt)
        val startGreen = Color.green(startColorInt)
        val startAlpha = Color.alpha(startColorInt)

        val disRed = Color.red(endColorInt) - startRed
        val disBlue = Color.blue(endColorInt) - startBlue
        val disGreen = Color.green(endColorInt) - startGreen
        val disAlpha = Color.alpha(endColorInt) - startAlpha

        val medRed = startRed + ratio * disRed
        val medBlue = startBlue + ratio * disBlue
        val medGreen = startGreen + ratio * disGreen
        val medAlpha = startAlpha + ratio * disAlpha

        return Color.argb(medAlpha, medRed, medGreen, medBlue)
    }

    /////////////////////////////////////////////////////////////////////////////////

    /**
     * @param ratio Float 比例 0-1
     * @return Int
     */
    @JvmStatic
    @ColorInt
    fun applyAdjustAlpha(@ColorInt colorInt: Int, ratio: Float): Int =
        Color.argb((Color.alpha(colorInt) * ratio).roundToInt(), Color.red(colorInt), Color.green(colorInt), Color.blue(colorInt))

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @ColorInt
    fun obj2colorInt(obj: Any): Int =
        when (obj) {
            is String -> obj.colorStr2colorInt()
            is Int -> obj
            else -> Color.WHITE
        }

    /**
     * 获取颜色
     * @param colorStr String
     * @return Int
     */
    @JvmStatic
    @ColorInt
    fun colorStr2colorInt(colorStr: String): Int =
        Color.parseColor(colorStr)

    @JvmStatic
    fun colorInt2colorStr(@ColorInt colorInt: Int): String =
        String.format("#%06X", 0xFFFFFF and colorInt).uppercase()
}