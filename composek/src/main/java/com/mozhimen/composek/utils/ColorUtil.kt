package com.mozhimen.composek.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter

/**
 * @ClassName ColorUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/13
 * @Version 1.0
 */
fun Long.longColor2color(): Color =
    ColorUtil.longColor2color(this)

fun Int.intColor2color(): Color =
    ColorUtil.intColor2color(this)

fun Long.longColor2colorPainter(): ColorPainter =
    ColorUtil.longColor2colorPainter(this)

fun Int.intColor2colorPainter(): ColorPainter =
    ColorUtil.intColor2colorPainter(this)

////////////////////////////////////////////////////////////////
object ColorUtil {
    @JvmStatic
    fun longColor2color(longColor: Long): Color =
        Color(longColor)

    @JvmStatic
    fun intColor2color(intColor: Int): Color =
        Color(intColor)

    @JvmStatic
    fun longColor2colorPainter(longColor: Long): ColorPainter =
        ColorPainter(longColor2color(longColor))

    @JvmStatic
    fun intColor2colorPainter(intColor: Int): ColorPainter =
        ColorPainter(intColor2color(intColor))
}