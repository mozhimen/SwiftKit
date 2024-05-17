package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.IntRange
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.core.graphics.component4
import androidx.core.graphics.drawable.toBitmap

/**
 * @ClassName UtilKDrawableFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:15
 * @Version 1.0
 */
fun Drawable.drawable2bitmapDrawable(drawable: Drawable): BitmapDrawable =
    UtilKDrawableFormat.drawable2bitmapDrawable(drawable)

fun Drawable.drawable2bitmap(width: Int = this.intrinsicWidth, height: Int = this.intrinsicHeight, config: Bitmap.Config? = null): Bitmap =
    UtilKDrawableFormat.drawable2bitmap(this, width, height, config)

object UtilKDrawableFormat {
    @JvmStatic
    fun drawable2bitmapDrawable(drawable: Drawable): BitmapDrawable =
        drawable as BitmapDrawable

    @JvmStatic
    fun drawable2bitmap(drawable: Drawable, width: Int = drawable.intrinsicWidth, height: Int = drawable.intrinsicHeight, config: Bitmap.Config? = null): Bitmap =
        drawable.toBitmap(width, height, config)
}