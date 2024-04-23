package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.kotlin.ranges.constraint
import com.mozhimen.basick.utilk.kotlin.strFilePath2bitmapAny
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

fun Bitmap.applyBitmapAnyResize(destWidth: Int, heightDest: Int, filter: Boolean = true): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyResize(this, destWidth, heightDest, filter)

fun Bitmap.applyBitmapAnyRotate(degree: Float, flipX: Boolean = false, flipY: Boolean = false): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyRotate(this, degree, flipX, flipY)

fun Bitmap.applyBitmapAnyScale(destWidth: Float, heightDest: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyScale(this, destWidth, heightDest)

fun Bitmap.applyBitmapAnyScaleRatio(@FloatRange(from = 0.0) ratio: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyScaleRatio(this, ratio)

fun Bitmap.applyBitmapAnyScaleRatio(@FloatRange(from = 0.0) ratioX: Float, @FloatRange(from = 0.0) ratioY: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyScaleRatio(this, ratioX, ratioY)

fun Bitmap.applyBitmapAnyZoom(bitmapSource: Bitmap, ratio: Float): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyZoom(this, ratio)

fun Bitmap.applyBitmapAnyCrop(rectF: RectF): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyCrop(this, rectF)

fun Bitmap.applyBitmapAnyCrop(width: Int, height: Int, x: Int, y: Int): Bitmap =
    UtilKBitmapDeal.applyBitmapAnyCrop(this, width, height, x, y)

///////////////////////////////////////////////////////////////////////////////

object UtilKBitmapDeal : BaseUtilK() {
    fun applyBitmapAnyCompress(bitmapSource: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100, outputStream: OutputStream) {
        UtilKBitmap.compress(bitmapSource, format, quality, outputStream)
    }

    //设置大小
    @JvmStatic
    fun applyBitmapAnyResize(bitmapSource: Bitmap, destWidth: Int, heightDest: Int, filter: Boolean = true): Bitmap =
        UtilKBitmap.createScaledBitmap(bitmapSource, destWidth, heightDest, filter)

    //旋转位图
    @JvmStatic
    fun applyBitmapAnyRotate(bitmapSource: Bitmap, degree: Float, flipX: Boolean = false, flipY: Boolean = false): Bitmap {
        val matrix = Matrix()
        UtilKMatrix.postRotate(matrix, degree)
        UtilKMatrix.postFlip(matrix, flipX, flipY)
        return UtilKBitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.width, bitmapSource.height, matrix, true)
    }

    //缩放原图
    @JvmStatic
    fun applyBitmapAnyScale(bitmapSource: Bitmap, destWidth: Float, heightDest: Float): Bitmap =
        applyBitmapAnyScaleRatio(bitmapSource, destWidth / bitmapSource.width.toFloat(), heightDest / bitmapSource.height.toFloat())

    //缩放原图
    @JvmStatic
    @Throws(Exception::class)
    fun applyBitmapAnyScaleRatio(bitmapSource: Bitmap, @FloatRange(from = 0.0) ratio: Float): Bitmap =
        applyBitmapAnyScaleRatio(bitmapSource, ratio, ratio)

    //缩放原图
    @JvmStatic
    @Throws(Exception::class)
    fun applyBitmapAnyScaleRatio(bitmapSource: Bitmap, @FloatRange(from = 0.0) ratioX: Float, @FloatRange(from = 0.0) ratioY: Float): Bitmap {
        require(ratioX > 0f && ratioY > 0) { "$TAG ratioX or ratioY must bigger than 0" }
        val matrix = Matrix()
        UtilKMatrix.postScale(matrix, ratioX, ratioY)
        return UtilKBitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.width, bitmapSource.height, matrix, true)
    }

    //同比例放大图片
    @JvmStatic
    fun applyBitmapAnyZoom(bitmapSource: Bitmap, @FloatRange(from = 0.0) ratio: Float): Bitmap {
        if (ratio <= 1) return bitmapSource
        val zoomWidth: Float = bitmapSource.width / ratio
        val zoomHeight: Float = bitmapSource.height / ratio
        val x: Float = (ratio - 1) * bitmapSource.width / 2f / ratio
        val y: Float = (ratio - 1) * bitmapSource.height / 2f / ratio
        return applyBitmapAnyCrop(bitmapSource, zoomWidth.toInt(), zoomHeight.toInt(), x.toInt(), y.toInt())
    }

    //裁剪图片
    @JvmStatic
    fun applyBitmapAnyCrop(bitmapSource: Bitmap, rectF: RectF): Bitmap {
        val left = rectF.left.constraint(0f,bitmapSource.width.toFloat())
        val top = rectF.top.constraint(0f,bitmapSource.height.toFloat())
        val right = rectF.right.constraint(0f, bitmapSource.width.toFloat())
        val bottom = rectF.bottom.constraint(0f, bitmapSource.height.toFloat())
        return applyBitmapAnyCrop(bitmapSource, (right - left).toInt(), (bottom - top).toInt(), left.toInt(), top.toInt())
    }

    //裁剪图片
    @JvmStatic
    fun applyBitmapAnyCrop(bitmapSource: Bitmap, @IntRange(from = 0) width: Int, @IntRange(from = 0) height: Int, x: Int, y: Int): Bitmap {
        UtilKLogWrapper.d(TAG, "applyBitmapAnyCrop: width $width height $height x $x y $y")
        val sourceWidth: Int = bitmapSource.width // 得到图片的宽，高
        val sourceHeight: Int = bitmapSource.height
        val cropWidth = if (width >= sourceWidth) sourceWidth else width // 裁切后所取的正方形区域边长
        val cropHeight = if (height >= sourceHeight) sourceHeight else height
        return UtilKBitmap.createBitmap(bitmapSource, x, y, cropWidth, cropHeight, null, false)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //将两个图片裁剪成一致
    @JvmStatic
    fun applyAnyBitmapScale2sameSize(bitmapSource1: Bitmap, bitmapSource2: Bitmap, ratio: Float = 1f): Pair<Bitmap, Bitmap> {
        val minWidth = min(bitmapSource1.width, bitmapSource2.width) * ratio
        val minHeight = min(bitmapSource1.height, bitmapSource2.height) * ratio
        return applyBitmapAnyScale(bitmapSource1, minWidth, minHeight) to applyBitmapAnyScaleRatio(bitmapSource2, minWidth, minHeight)
    }

    //匹配角度
    @JvmStatic
    fun applyAnyBitmapAdjustRotation(bitmapSource: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        UtilKMatrix.applyRotate(matrix, degree.toFloat(), bitmapSource.width.toFloat() / 2, bitmapSource.height.toFloat() / 2)
        val outputX: Float
        val outputY: Float
        if (degree == 90) {
            outputX = bitmapSource.height.toFloat()
            outputY = 0f
        } else {
            outputX = bitmapSource.height.toFloat()
            outputY = bitmapSource.width.toFloat()
        }
        val values = FloatArray(9)
        UtilKMatrix.getValues(matrix, values)
        UtilKMatrix.postTranslate(matrix, outputX - values[Matrix.MTRANS_X], outputY - values[Matrix.MTRANS_Y])
        val destBitmap = UtilKBitmap.createBitmap(bitmapSource.height, bitmapSource.width, Bitmap.Config.ARGB_8888)
        Canvas(destBitmap).drawBitmap(bitmapSource, matrix, Paint())
        return destBitmap
    }

    //滤镜图片
    @JvmStatic
    fun applyAnyBitmapFilter(bitmapSource: Bitmap, @ColorInt filterColorInt: Int): Bitmap {
        val destBitmap = UtilKBitmap.createBitmap(bitmapSource.width, bitmapSource.height, bitmapSource.config)
        Canvas(destBitmap).drawBitmap(bitmapSource, 0f, 0f, Paint().apply {
            colorFilter = PorterDuffColorFilter(filterColorInt, PorterDuff.Mode.SRC_IN)
        })
        return destBitmap
    }

    //将本地图片文件转换成可解码二维码的 Bitmap,为了避免图片太大，这里对图片进行了压缩
    @JvmStatic
    fun applyAnyBitmapDecode(strFilePathName: String): Bitmap? =
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            strFilePathName.strFilePath2bitmapAny(options)
            var sampleSize = options.outHeight / 400
            if (sampleSize <= 0)
                sampleSize = 1
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
            strFilePathName.strFilePath2bitmapAny(options)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
            null
        }

    //堆叠Bitmap,最左边的在下面
    @JvmStatic
    fun applyAnyBitmapPileUp(bgSourceBitmap: Bitmap, fgSourceBitmap: Bitmap): Bitmap {
        val destBitmap = UtilKBitmap.createBitmap(bgSourceBitmap.width, bgSourceBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(destBitmap)
        canvas.drawBitmap(bgSourceBitmap, 0f, 0f, null)
        canvas.drawBitmap(fgSourceBitmap, ((bgSourceBitmap.height - fgSourceBitmap.width) / 2).toFloat(), ((bgSourceBitmap.height - fgSourceBitmap.height) / 2).toFloat(), null)
        return destBitmap
    }
}