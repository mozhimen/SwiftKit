package com.mozhimen.basick.utilk.android.graphics

import android.app.Activity
import android.graphics.*
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.view.UtilKVirtualBar
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import java.lang.Integer.min

/**
 * @ClassName UtilKBitmapDeal
 * @Description Bitmap 处理类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 22:49
 * @Version 1.0
 */
object UtilKBitmapDeal : BaseUtilK() {
    /**
     * 设置大小
     */
    @JvmStatic
    fun resizeBitmap(bitmap: Bitmap, destWidth: Int, destHeight: Int, filter: Boolean = true): Bitmap =
        Bitmap.createScaledBitmap(bitmap, destWidth, destHeight, filter)

    /**
     * 旋转位图
     */
    @JvmStatic
    fun rotateBitmap(sourceBitmap: Bitmap, degree: Int, flipX: Boolean = false, flipY: Boolean = false): Bitmap {
        val matrix = Matrix()
        matrix.postRotate((degree).toFloat())
        matrix.postScale(if (flipX) -1f else 1f, if (flipY) -1f else 1f)
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, true)
    }

    /**
     * 将两个图片裁剪成一致
     */
    @JvmStatic
    fun scaleBitmap2SameSize(sourceBitmap1: Bitmap, sourceBitmap2: Bitmap,@FloatRange() ratio: Float = 1f): Pair<Bitmap, Bitmap> {
        val minWidth = min(sourceBitmap1.width, sourceBitmap2.width) * ratio
        val minHeight = min(sourceBitmap1.height, sourceBitmap2.height) * ratio
        return scaleBitmap(sourceBitmap1, minWidth, minHeight) to scaleBitmapRatio(sourceBitmap2, minWidth, minHeight)
    }

    /**
     * 缩放原图
     */
    @JvmStatic
    fun scaleBitmap(sourceBitmap: Bitmap, destWidth: Float, destHeight: Float): Bitmap =
        scaleBitmapRatio(sourceBitmap, destWidth / sourceBitmap.width.toFloat(), destHeight / sourceBitmap.height.toFloat())

    /**
     * 缩放原图
     */
    @JvmStatic
    @Throws(Exception::class)
    fun scaleBitmapRatio(sourceBitmap: Bitmap, @FloatRange(from = 0.0) ratio: Float): Bitmap =
        scaleBitmapRatio(sourceBitmap, ratio, ratio)

    /**
     * 缩放原图
     */
    @JvmStatic
    @Throws(Exception::class)
    fun scaleBitmapRatio(sourceBitmap: Bitmap, ratioX: Float, ratioY: Float): Bitmap {
        require(ratioX > 0f && ratioY > 0) { "$TAG ratioX or ratioY must bigger than 0" }
        val matrix = Matrix()
        matrix.postScale(ratioX, ratioY)
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, true)
    }

    /**
     * 同比例放大图片
     * @return Bitmap
     */
    @JvmStatic
    fun zoomBitmap(sourceBitmap: Bitmap, ratio: Float): Bitmap {
        if (ratio <= 1) return sourceBitmap
        val zoomWidth: Float = sourceBitmap.width / ratio
        val zoomHeight: Float = sourceBitmap.height / ratio
        val x: Float = (ratio - 1) * sourceBitmap.width / 2f / ratio
        val y: Float = (ratio - 1) * sourceBitmap.height / 2f / ratio
        return cropBitmap(sourceBitmap, zoomWidth.toInt(), zoomHeight.toInt(), x.toInt(), y.toInt())
    }

    /**
     * 裁剪图片
     */
    @JvmStatic
    fun cropBitmap(sourceBitmap: Bitmap, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val sourceWidth: Int = sourceBitmap.width // 得到图片的宽，高
        val sourceHeight: Int = sourceBitmap.height
        val cropWidth = if (width >= sourceWidth) sourceWidth else width // 裁切后所取的正方形区域边长
        val cropHeight = if (height >= sourceHeight) sourceHeight else height
        return Bitmap.createBitmap(sourceBitmap, x, y, cropWidth, cropHeight, null, false)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 匹配角度
     */
    @JvmStatic
    fun adjustBitmapRotation(sourceBitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.setRotate(degree.toFloat(), sourceBitmap.width.toFloat() / 2, sourceBitmap.height.toFloat() / 2)
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
        matrix.getValues(values)
        matrix.postTranslate(outputX - values[Matrix.MTRANS_X], outputY - values[Matrix.MTRANS_Y])
        val destBitmap = Bitmap.createBitmap(sourceBitmap.height, sourceBitmap.width, Bitmap.Config.ARGB_8888)
        Canvas(destBitmap).drawBitmap(sourceBitmap, matrix, Paint())
        return destBitmap
    }

    /**
     * 滤镜图片
     */
    @JvmStatic
    fun filterBitmap(sourceBitmap: Bitmap, @ColorInt filterColorInt: Int): Bitmap {
        val destBitmap = Bitmap.createBitmap(sourceBitmap.width, sourceBitmap.height, sourceBitmap.config)
        Canvas(destBitmap).drawBitmap(sourceBitmap, 0f, 0f, Paint().apply { colorFilter = PorterDuffColorFilter(filterColorInt, PorterDuff.Mode.SRC_IN) })
        return destBitmap
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap,为了避免图片太大，这里对图片进行了压缩
     */
    @JvmStatic
    fun getDecodableBitmap(filePathWithName: String): Bitmap? {
        return try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePathWithName, options)
            var sampleSize = options.outHeight / 400
            if (sampleSize <= 0) {
                sampleSize = 1
            }
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
            BitmapFactory.decodeFile(filePathWithName, options)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }
    }

    /**
     * 堆叠Bitmap,最左边的在下面
     */
    @JvmStatic
    fun pileUpBitmap(bgSourceBitmap: Bitmap, fgSourceBitmap: Bitmap): Bitmap {
        val destBitmap = Bitmap.createBitmap(bgSourceBitmap.width, bgSourceBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(destBitmap)
        canvas.drawBitmap(bgSourceBitmap, 0f, 0f, null)
        canvas.drawBitmap(fgSourceBitmap, ((bgSourceBitmap.height - fgSourceBitmap.width) / 2).toFloat(), ((bgSourceBitmap.height - fgSourceBitmap.height) / 2).toFloat(), null)
        return destBitmap
    }

    /**
     * 截屏
     */
    @JvmStatic
    fun getBitmapForScreen(activity: Activity): Bitmap {
        val view = UtilKDecorView.get(activity)
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.drawingCache, 0, 0, view.measuredWidth, view.measuredHeight - UtilKVirtualBar.getHeight(activity))
        view.isDrawingCacheEnabled = false
        view.destroyDrawingCache()
        return bitmap
    }
}