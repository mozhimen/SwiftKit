package com.mozhimen.uicorek.drawablek

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt

/**
 * @ClassName DrawableKRectangle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/20 12:00
 * @Version 1.0
 */
class DrawableKRectangle(radius: Float, @ColorInt backgroundColor: Int) : GradientDrawable() {
    init {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = radius
        setColor(backgroundColor)
    }
}