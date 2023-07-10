package com.mozhimen.basick.imagek.glide.temps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CircleCrop

/**
 * @ClassName CircleBorderTransform
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/10 17:28
 * @Version 1.0
 */
class CircleBorderTransform(private val _borderWidth: Float, borderColor: Int) : CircleCrop() {
    private val _borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        _borderPaint.color = borderColor
        _borderPaint.style = Paint.Style.STROKE
        _borderPaint.strokeWidth = _borderWidth
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val radiusWidth = outWidth / 2f
        val radiusHeight = outHeight / 2f
        canvas.drawCircle(
            radiusWidth,
            radiusHeight,
            radiusWidth.coerceAtMost(radiusHeight) - _borderWidth / 2f,
            _borderPaint
        )
        canvas.setBitmap(null)
        return transform
    }
}