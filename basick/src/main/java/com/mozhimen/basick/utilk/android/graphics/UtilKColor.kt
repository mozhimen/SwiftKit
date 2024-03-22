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
object UtilKColor {

    /**
     * 渐变色值
     * @param intColorStart Int 开始颜色
     * @param intColorEnd Int 结束颜色
     */
    @JvmStatic
    @ColorInt
    @RequiresApi(CVersCode.V_26_8_O)
    fun get_ofMedian(@ColorInt intColorStart: Int, @ColorInt intColorEnd: Int, ratio: Float): Int {
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

        return Color.argb(medAlpha, medRed, medGreen, medBlue)
    }
}