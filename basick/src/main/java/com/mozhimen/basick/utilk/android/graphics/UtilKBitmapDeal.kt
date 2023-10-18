package com.mozhimen.basick.utilk.android.graphics

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.kotlin.strFilePath2anyBitmap
import java.io.OutputStream
import java.lang.Integer.min

/**
 * @ClassName UtilKBitmapDeal
 * @Description Bitmap 处理类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 22:49
 * @Version 1.0
 */
fun Bitmap.applyBitmapAnyCompress(format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100, stream: OutputStream) {
    UtilKBitmapDeal.applyBitmapAnyCompress(this, format, quality, stream)
}

fun Bitmap.applyBitmapAnyResize(destWidth: Int, destHeight: Int, filter: Boolean = true) =
    UtilKBitmapDeal.applyBitmapAnyResize(this, destWidth, destHeight, filter)

fun Bitmap.applyBitmapAnyRotate(degree: Float, flipX: Boolean = false, flipY: Boolean = false): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyRotate(this, degree, flipX, flipY)

fun Bitmap.applyBitmapAnyScale(destWidth: Float, destHeight: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyScale(this, destWidth, destHeight)

fun Bitmap.applyBitmapAnyScaleRatio(@FloatRange(from = 0.0) ratio: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyScaleRatio(this, ratio)

fun Bitmap.applyBitmapAnyScaleRatio(@FloatRange(from = 0.0) ratioX: Float, @FloatRange(from = 0.0) ratioY: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyScaleRatio(this, ratioX, ratioY)

fun Bitmap.applyBitmapAnyZoom(sourceBitmap: Bitmap, ratio: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyZoom(this, ratio)

fun Bitmap.applyBitmapAnyCrop(width: Int, height: Int, x: Int, y: Int): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyCrop(this, width, height, x, y)

object UtilKBitmapDeal : BaseUtilK() {
    fun applyBitmapAnyCompress(sourceBitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100, stream: OutputStream) {
        sourceBitmap.compress(format, quality, stream)
    }

    /**
     * 设置大小
     */
    @JvmStatic
    fun applyBitmapAnyResize(sourceBitmap: Bitmap, destWidth: Int, destHeight: Int, filter: Boolean = true): Bitmap =
        Bitmap.createScaledBitmap(sourceBitmap, destWidth, destHeight, filter)

    /**
     * 旋转位图
     */
    @JvmStatic
    fun applyBitmapAnyRotate(sourceBitmap: Bitmap, degree: Float, flipX: Boolean = false, flipY: Boolean = false): Bitmap {
        val matrix = Matrix()
        UtilKMatrix.postRotate(matrix, degree)
        UtilKMatrix.postFlip(matrix, flipX, flipY)
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, true)
    }

    /**
     * 缩放原图
     */
    @JvmStatic
    fun applyBitmapAnyScale(sourceBitmap: Bitmap, destWidth: Float, destHeight: Float): Bitmap =
        applyBitmapAnyScaleRatio(sourceBitmap, destWidth / sourceBitmap.width.toFloat(), destHeight / sourceBitmap.height.toFloat())

    /**
     * 缩放原图
     */
    @JvmStatic
    @Throws(Exception::class)
    fun applyBitmapAnyScaleRatio(sourceBitmap: Bitmap, @FloatRange(from = 0.0) ratio: Float): Bitmap =
        applyBitmapAnyScaleRatio(sourceBitmap, ratio, ratio)

    /**
     * 缩放原图
     */
    @JvmStatic
    @Throws(Exception::class)
    fun applyBitmapAnyScaleRatio(sourceBitmap: Bitmap, @FloatRange(from = 0.0) ratioX: Float, @FloatRange(from = 0.0) ratioY: Float): Bitmap {
        require(ratioX > 0f && ratioY > 0) { "$TAG ratioX or ratioY must bigger than 0" }
        val matrix = Matrix()
        UtilKMatrix.postScale(matrix, ratioX, ratioY)
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, true)
    }

    /**
     * 同比例放大图片
     * @return Bitmap
     */
    @JvmStatic
    fun applyBitmapAnyZoom(sourceBitmap: Bitmap, @FloatRange(from = 0.0) ratio: Float): Bitmap {
        if (ratio <= 1) return sourceBitmap
        val zoomWidth: Float = sourceBitmap.width / ratio
        val zoomHeight: Float = sourceBitmap.height / ratio
        val x: Float = (ratio - 1) * sourceBitmap.width / 2f / ratio
        val y: Float = (ratio - 1) * sourceBitmap.height / 2f / ratio
        return applyBitmapAnyCrop(sourceBitmap, zoomWidth.toInt(), zoomHeight.toInt(), x.toInt(), y.toInt())
    }

    /**
     * 裁剪图片
     */
    @JvmStatic
    fun applyBitmapAnyCrop(sourceBitmap: Bitmap, @IntRange(from = 0) width: Int, @IntRange(from = 0) height: Int, x: Int, y: Int): Bitmap {
        val sourceWidth: Int = sourceBitmap.width // 得到图片的宽，高
        val sourceHeight: Int = sourceBitmap.height
        val cropWidth = if (width >= sourceWidth) sourceWidth else width // 裁切后所取的正方形区域边长
        val cropHeight = if (height >= sourceHeight) sourceHeight else height
        return Bitmap.createBitmap(sourceBitmap, x, y, cropWidth, cropHeight, null, false)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 将两个图片裁剪成一致
     */
    @JvmStatic
    fun applyAnyBitmapScale2sameSize(sourceBitmap1: Bitmap, sourceBitmap2: Bitmap, ratio: Float = 1f): Pair<Bitmap, Bitmap> {
        val minWidth = min(sourceBitmap1.width, sourceBitmap2.width) * ratio
        val minHeight = min(sourceBitmap1.height, sourceBitmap2.height) * ratio
        return applyBitmapAnyScale(sourceBitmap1, minWidth, minHeight) to applyBitmapAnyScaleRatio(sourceBitmap2, minWidth, minHeight)
    }

    /**
     * 匹配角度
     */
    @JvmStatic
    fun applyAnyBitmapAdjustRotation(sourceBitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        UtilKMatrix.applyRotate(matrix, degree.toFloat(), sourceBitmap.width.toFloat() / 2, sourceBitmap.height.toFloat() / 2)
        val outputX: Float
        val outputY: Float
        if (degree == 90) {
            outputX = sourceBitmap.height.toFloat()
            outputY = 0f
        } else {
            outputX = sourceBitmap.height.toFloat()
            outputY = sourceBitmap.width.toFloat()
        }
        val values = FloatArray(9)
        UtilKMatrix.getValues(matrix, values)
        UtilKMatrix.postTranslate(matrix, outputX - values[Matrix.MTRANS_X], outputY - values[Matrix.MTRANS_Y])
        val destBitmap = Bitmap.createBitmap(sourceBitmap.height, sourceBitmap.width, Bitmap.Config.ARGB_8888)
        Canvas(destBitmap).drawBitmap(sourceBitmap, matrix, Paint())
        return destBitmap
    }

    /**
     * 滤镜图片
     */
    @JvmStatic
    fun applyAnyBitmapFilter(sourceBitmap: Bitmap, @ColorInt filterColorInt: Int): Bitmap {
        val destBitmap = Bitmap.createBitmap(sourceBitmap.width, sourceBitmap.height, sourceBitmap.config)
        Canvas(destBitmap).drawBitmap(sourceBitmap, 0f, 0f, Paint().apply {
            colorFilter = PorterDuffColorFilter(filterColorInt, PorterDuff.Mode.SRC_IN)
        })
        return destBitmap
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap,为了避免图片太大，这里对图片进行了压缩
     */
    @JvmStatic
    fun applyAnyBitmapDecode(filePathWithName: String): Bitmap? =
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            filePathWithName.strFilePath2anyBitmap(options)
            var sampleSize = options.outHeight / 400
            if (sampleSize <= 0)
                sampleSize = 1
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
            filePathWithName.strFilePath2anyBitmap(options)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    /**
     * 堆叠Bitmap,最左边的在下面
     */
    @JvmStatic
    fun applyAnyBitmapPileUp(bgSourceBitmap: Bitmap, fgSourceBitmap: Bitmap): Bitmap {
        val destBitmap = Bitmap.createBitmap(bgSourceBitmap.width, bgSourceBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(destBitmap)
        canvas.drawBitmap(bgSourceBitmap, 0f, 0f, null)
        canvas.drawBitmap(fgSourceBitmap, ((bgSourceBitmap.height - fgSourceBitmap.width) / 2).toFloat(), ((bgSourceBitmap.height - fgSourceBitmap.height) / 2).toFloat(), null)
        return destBitmap
    }
}