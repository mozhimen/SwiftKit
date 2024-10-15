package com.mozhimen.composek.utils.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.ColorPainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * @ClassName ColorUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/13
 * @Version 1.0
 */
fun Long.longColor2color(): Color =
    UtilKColor.longColor2color(this)

fun Int.intColor2color(): Color =
    UtilKColor.intColor2color(this)

fun Long.longColor2colorPainter(): ColorPainter =
    UtilKColor.longColor2colorPainter(this)

fun Int.intColor2colorPainter(): ColorPainter =
    UtilKColor.intColor2colorPainter(this)

fun Long.longColor2solidColor(): SolidColor =
    UtilKColor.longColor2solidColor(this)

fun Int.intColor2solidColor(): SolidColor =
    UtilKColor.intColor2solidColor(this)

////////////////////////////////////////////////////////////////

object UtilKColor {
    @JvmStatic
    fun getRandomColor():Color =
        Color(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256),
            alpha = 255
        )

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

    @JvmStatic
    fun intColor2solidColor(intColor: Int): SolidColor =
        SolidColor(intColor.intColor2color())

    @JvmStatic
    fun longColor2solidColor(longColor: Long): SolidColor =
        SolidColor(longColor.longColor2color())
}