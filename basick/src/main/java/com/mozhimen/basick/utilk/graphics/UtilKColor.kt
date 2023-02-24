package com.mozhimen.basick.utilk.graphics

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode

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
    @JvmStatic
    @ColorInt
    @RequiresApi(CVersionCode.V_26_8_O)
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
     * @param colorStr String
     * @return Int
     */
    @JvmStatic
    @ColorInt
    fun colorStr2Int(colorStr: String): Int = Color.parseColor(colorStr)

    /**
     * 获取颜色
     * @param obj Any
     * @return Int
     */
    @JvmStatic
    @ColorInt
    fun colorStr2Int(obj: Any): Int = when (obj) {
        is String -> colorStr2Int(obj)
        is Int -> obj
        else -> Color.WHITE
    }
}