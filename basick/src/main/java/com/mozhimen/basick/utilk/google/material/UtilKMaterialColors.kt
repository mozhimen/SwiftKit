package com.mozhimen.basick.utilk.google.material

import androidx.annotation.FloatRange
import com.google.android.material.color.MaterialColors

/**
 * @ClassName UtilKMaterialColors
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/29
 * @Version 1.0
 */
object UtilKMaterialColors {
    @JvmStatic
    fun compositeARGBWithAlpha(intColor: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int =
        MaterialColors.compositeARGBWithAlpha(intColor, (alpha * 255f).toInt())
}