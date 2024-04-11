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

    @JvmStatic
    @ColorInt
    @RequiresApi(CVersCode.V_26_8_O)
    fun argb(alpha: Float, red: Float, green: Float, blue: Float): Int =
        Color.argb(alpha, red, green, blue)


    fun argb(alpha: Int, red: Int, green: Int, blue: Int): Int =
        Color.argb(alpha, red, green, blue)
}