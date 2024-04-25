package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Paint

/**
 * @ClassName UtilKPaint
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/25
 * @Version 1.0
 */
object UtilKPaint {
    @JvmStatic
    fun getFontMetricsInt(paint: Paint): Paint.FontMetricsInt =
        paint.fontMetricsInt
}