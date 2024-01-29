package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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

fun Drawable.drawable2bitmapOfCustom(width: Int = this.intrinsicWidth, height: Int = this.intrinsicHeight): Bitmap =
    UtilKDrawableFormat.drawable2bitmapOfCustom(this, width, height)

object UtilKDrawableFormat {
    @JvmStatic
    fun drawable2bitmapDrawable(drawable: Drawable): BitmapDrawable =
        drawable as BitmapDrawable

    @JvmStatic
    fun drawable2bitmap(drawable: Drawable, width: Int = drawable.intrinsicWidth, height: Int = drawable.intrinsicHeight, config: Bitmap.Config? = null): Bitmap =
        drawable.toBitmap(width, height, config)

    @JvmStatic
    fun drawable2bitmapOfCustom(drawable: Drawable, width: Int = drawable.intrinsicWidth, height: Int = drawable.intrinsicHeight): Bitmap =
        if (drawable is BitmapDrawable) drawable.bitmap else {
            val bitmap: Bitmap = if (width <= 0 || height <= 0) {
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
}