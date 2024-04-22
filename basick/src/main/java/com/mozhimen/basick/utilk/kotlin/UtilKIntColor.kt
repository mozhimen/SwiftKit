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
fun Int.intColor2strColor(): String =
    UtilKIntColor.intColor2strColor(this)

/////////////////////////////////////////////////////////////////////////

object UtilKIntColor {

    @JvmStatic
    fun intColor2strColor(@ColorInt intColor: Int): String =
        String.format("#%06X", 0xFFFFFF and intColor)
}