package com.mozhimen.basicsk.utilk

import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

/**
 * @ClassName UtilKColor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/9 15:05
 * @Version 1.0
 */
object UtilKColor {
    /**
     * 渐变色值
     * @param startColor Int 开始颜色
     * @param endColor Int 结束颜色
     * @param ratio Float
     * @return Int
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMedianColor(startColor: Int, endColor: Int, ratio: Float): Int {
        val startRed = Color.red(startColor)
        val startBlue = Color.blue(startColor)
        val startGreen = Color.green(startColor)
        val startAlpha = Color.alpha(startColor)

        val endRed = Color.red(endColor)
        val endBlue = Color.blue(endColor)
        val endGreen = Color.green(endColor)
        val endAlpha = Color.alpha(endColor)

        val disRed = endRed - startRed
        val disBlue = endBlue - startBlue
        val disGreen = endGreen - startGreen
        val disAlpha = endAlpha - startAlpha

        val medRed = startRed + ratio * disRed
        val medBlue = startBlue + ratio * disBlue
        val medGreen = startGreen + ratio * disGreen
        val medAlpha = startAlpha + ratio * disAlpha

        return Color.argb(medAlpha, medRed, medGreen, medBlue)
    }

    /**
     * 获取颜色
     * @param color Any
     * @return Int
     */
    @ColorInt
    fun getColorTone(color: Any): Int = when (color) {
        is String -> {
            Color.parseColor(color)
        }
        is Int -> {
            color
        }
        else -> {
            throw IllegalArgumentException("color type is illegal!")
        }
    }
}