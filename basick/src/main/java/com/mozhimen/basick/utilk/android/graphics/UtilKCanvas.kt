package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Canvas
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

/**
 * @ClassName UtilKCanvas
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/14 14:42
 * @Version 1.0
 */
object UtilKCanvas {

    @JvmStatic
    fun applyDrawStr(canvas: Canvas, textPaint: TextPaint, str: String, x: Float, y: Float, alignment: Layout.Alignment) {
        canvas.save()
        canvas.translate(x, y)
        val staticLayout = StaticLayout(str, textPaint, textPaint.measureText(str).toInt(), alignment, 1f, 0f, false)
        staticLayout.draw(canvas)
        canvas.restore()
    }
}