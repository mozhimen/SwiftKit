package com.mozhimen.basick.utilk.android.graphics

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.Px

/**
 * @ClassName UtilKGradientDrawable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/31 12:01
 * @Version 1.0
 */
object UtilKGradientDrawable {
    fun get(shape: Int, @ColorInt intColor: Int, @ColorInt intColorBorder: Int, @Px intPxWidthBorder: Int): GradientDrawable =
        GradientDrawable().apply {
            this.shape = shape
            setColor(intColor)
            if (intPxWidthBorder > 0 && intColorBorder != intColor) {
                setStroke(intPxWidthBorder, intColorBorder)
            }
        }

    fun get_ofRect(@ColorInt intColor: Int, @Px radius: Float): GradientDrawable =
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(intColor)
            cornerRadius = radius
        }

    fun get_ofRectBorder(@ColorInt intColor: Int, @Px radius: Float, @Px width: Int, @ColorInt intColorBorder: Int): GradientDrawable =
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(intColor)
            cornerRadius = radius
            setStroke(width, intColorBorder)
        }
}