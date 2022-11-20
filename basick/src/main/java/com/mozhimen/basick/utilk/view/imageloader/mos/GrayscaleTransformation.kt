package com.mozhimen.basick.utilk.view.imageloader.mos

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import coil.size.Size
import coil.transform.Transformation
import com.mozhimen.basick.utilk.view.imageloader.commons.ITransformation

/**
 * @ClassName GrayscaleTransformation
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 18:45
 * @Version 1.0
 */
class GrayscaleTransformation : Transformation, ITransformation {

    override val cacheKey: String = GrayscaleTransformation::class.java.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        paint.colorFilter = COLOR_FILTER

        val output = createBitmap(input.width, input.height, input.safeConfig)
        output.applyCanvas {
            drawBitmap(input, 0f, 0f, paint)
        }

        return output
    }

    override fun equals(other: Any?) = other is GrayscaleTransformation

    override fun hashCode() = javaClass.hashCode()

    override fun toString() = "GrayscaleTransformation()"

    private companion object {
        val COLOR_FILTER = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
    }
}