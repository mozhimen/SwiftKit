package com.mozhimen.basick.utilk.kotlin

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.graphics.UtilKColor
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * @ClassName UtilKIntColor
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:13
 * @Version 1.0
 */
fun Int.intColor2strColor(): String =
    UtilKIntColor.intColor2strColor(this)

/////////////////////////////////////////////////////////////////////////

object UtilKIntColor {

    @JvmStatic
    fun get_ofRandom(
        seed: Any,
        paletteSize: Int = 128,
        saturation: Float = 0.5f,
        lightness: Float = 0.5f
    ): Int {
        val hue = abs(seed.hashCode() % paletteSize) / paletteSize.toFloat() * 360f
        return ColorUtils.HSLToColor(floatArrayOf(hue, saturation, lightness))
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun intColor2intColorRgba(intColor: Int): List<Int> =
        listOf(Color.red(intColor), Color.green(intColor), Color.blue(intColor), Color.alpha(intColor))


    @JvmStatic
    fun intColor2intColorRgb(intColor: Int):List<Int> =
        intColor2intColorRgba(intColor).take(3)

    @JvmStatic
    fun intColorRgba(intColorRgba:List<Int>):Int =
        UtilKColor.argb(intColorRgba[3], intColorRgba[0], intColorRgba[1], intColorRgba[2])

    @JvmStatic
    fun intColor2strColor(@ColorInt intColor: Int): String =
        String.format("#%06X", 0xFFFFFF and intColor)
}