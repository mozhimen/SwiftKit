package com.mozhimen.basick.utilk.view.imageloader.mos

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.createBitmap
import coil.size.Size
import coil.transform.Transformation
import com.mozhimen.basick.utilk.view.imageloader.commons.ITransformation
import java.lang.Float.max

/**
 * @ClassName CropTransformation
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 19:11
 * @Version 1.0
 */
class CropTransformation(
    private val cropType: CropType = CropType.CENTER
) : Transformation, ITransformation {

    enum class CropType {
        TOP,
        CENTER,
        BOTTOM
    }

    override val cacheKey: String = "${CropTransformation::class.java.name}-$cropType"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val width = input.width
        val height = input.height

        val output = createBitmap(width, height, input.safeConfig)

        output.setHasAlpha(true)

        val scaleX = width.toFloat() / input.width
        val scaleY = height.toFloat() / input.height
        val scale = max(scaleX, scaleY)

        val scaledWidth = scale * input.width
        val scaledHeight = scale * input.height
        val left = (width - scaledWidth) / 2
        val top = getTop(height.toFloat(), scaledHeight)
        val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

        val canvas = Canvas(output)
        canvas.drawBitmap(input, null, targetRect, null)

        return output
    }

    private fun getTop(height: Float, scaledHeight: Float): Float {
        return when (cropType) {
            CropType.TOP -> 0f
            CropType.CENTER -> (height - scaledHeight) / 2
            CropType.BOTTOM -> height - scaledHeight
        }
    }
}