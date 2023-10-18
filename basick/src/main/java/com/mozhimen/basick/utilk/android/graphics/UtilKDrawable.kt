package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap

/**
 * @ClassName UtilKDrawable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/22 22:42
 * @Version 1.0
 */
fun Drawable.applyColorFilter(@ColorInt colorInt: Int) {
    UtilKDrawable.applyColorFilter(this, colorInt)
}

object UtilKDrawable {

    /**
     * 是否正常的drawable
     * @param drawable Drawable
     * @return Boolean
     */
    @JvmStatic
    fun isColorDrawableNormal(drawable: Drawable): Boolean {
        return drawable !is ColorDrawable || drawable.color != Color.TRANSPARENT
    }

    /////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyColorFilter(drawable: Drawable, @ColorInt colorInt: Int) {
        drawable.mutate().setColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
    }
}