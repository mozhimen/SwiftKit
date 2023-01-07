package com.mozhimen.basick.utilk.bitmap

import android.graphics.*
import java.lang.Integer.min

/**
 * @ClassName UtilKBitmapDeal
 * @Description Bitmap 处理类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 22:49
 * @Version 1.0
 */
object UtilKBitmapDeal {
    private const val TAG = "UtilKBitmapDeal>>>>>"

    /**
     * bitmap转化为rgb565
     * @param sourceBitmap Bitmap
     * @return Bitmap
     */
    @JvmStatic
    fun bitmap2Rgb565Bitmap(sourceBitmap: Bitmap): Bitmap {
        return sourceBitmap.copy(Bitmap.Config.RGB_565, true)
    }

    /**
     * 旋转位图
     * @param sourceBitmap Bitmap
     * @param degree Int
     * @param flipX Boolean
     * @param flipY Boolean
     * @return Bitmap
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
     * @param sourceBitmap1 Bitmap
     * @param sourceBitmap2 Bitmap
     * @param ratio Float
     * @return Pair<Bitmap, Bitmap>
     */
    @JvmStatic
    fun scaleSameSize(sourceBitmap1: Bitmap, sourceBitmap2: Bitmap, ratio: Float = 1f): Pair<Bitmap, Bitmap> {
        val minWidth = min(sourceBitmap1.width, sourceBitmap2.width) * ratio
        val minHeight = min(sourceBitmap1.height, sourceBitmap2.height) * ratio
        return scaleBitmap(sourceBitmap1, minWidth.toInt(), minHeight.toInt()) to scaleBitmap(sourceBitmap2, minWidth.toInt(), minHeight.toInt())
    }

    /**
     * 缩放原图
     * @param sourceBitmap Bitmap
     * @param ratio Float
     * @return Bitmap
     */
    @JvmStatic
    @Throws(Exception::class)
    fun scaleBitmap(sourceBitmap: Bitmap, ratio: Float): Bitmap {
        require(ratio > 0f) { "$TAG ratioX or ratioY must bigger than 0" }
        return scaleBitmap(sourceBitmap, ratio, ratio)
    }

    /**
     * 缩放原图
     * @param sourceBitmap Bitmap
     * @param destWidth Float
     * @param destHeight Float
     * @return Bitmap
     */
    @JvmStatic
    fun scaleBitmap(sourceBitmap: Bitmap, destWidth: Int, destHeight: Int): Bitmap {
        val ratioX: Float = destWidth.toFloat() / sourceBitmap.width.toFloat()
        val ratioY: Float = destHeight.toFloat() / sourceBitmap.height.toFloat()
        return scaleBitmap(sourceBitmap, ratioX, ratioY)
    }

    /**
     * 缩放原图
     * @param sourceBitmap Bitmap
     * @param ratioX Float
     * @param ratioY Float
     * @return Bitmap
     */
    @JvmStatic
    @Throws(Exception::class)
    fun scaleBitmap(sourceBitmap: Bitmap, ratioX: Float, ratioY: Float): Bitmap {
        require(ratioX > 0f && ratioY > 0) { "$TAG ratioX or ratioY must bigger than 0" }
        val matrix = Matrix()
        matrix.postScale(ratioX, ratioY)
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, true)
    }

    /**
     * 同比例放大图片
     * @param sourceBitmap Bitmap
     * @param ratio Int
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
     * @param sourceBitmap Bitmap
     * @param width Int
     * @param height Int
     * @param x Int
     * @param y Int
     * @return Bitmap
     */
    @JvmStatic
    fun cropBitmap(sourceBitmap: Bitmap, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val sourceWidth: Int = sourceBitmap.width // 得到图片的宽，高
        val sourceHeight: Int = sourceBitmap.height
        val cropWidth = if (width >= sourceWidth) sourceWidth else width // 裁切后所取的正方形区域边长
        val cropHeight = if (height >= sourceHeight) sourceHeight else height
        return Bitmap.createBitmap(sourceBitmap, x, y, cropWidth, cropHeight, null, false)
    }

    /**
     * 匹配角度
     * @param sourceBitmap Bitmap
     * @param degree Int
     * @return Bitmap
     */
    @JvmStatic
    fun adjustBitmapRotation(sourceBitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.setRotate(
            degree.toFloat(),
            sourceBitmap.width.toFloat() / 2,
            sourceBitmap.height.toFloat() / 2
        )
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
        val x1 = values[Matrix.MTRANS_X]
        val y1 = values[Matrix.MTRANS_Y]
        matrix.postTranslate(outputX - x1, outputY - y1)
        val destBitmap = Bitmap.createBitmap(sourceBitmap.height, sourceBitmap.width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(destBitmap)
        canvas.drawBitmap(sourceBitmap, matrix, Paint())
        return destBitmap
    }

    /**
     * 滤镜图片
     * @param sourceBitmap Bitmap
     * @param tintColor Int 过滤颜色
     * @return Bitmap
     */
    @JvmStatic
    fun tintBitmap(sourceBitmap: Bitmap, tintColor: Int): Bitmap {
        val destBitmap = Bitmap.createBitmap(sourceBitmap.width, sourceBitmap.height, sourceBitmap.config)
        val canvas = Canvas(destBitmap)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(sourceBitmap, 0f, 0f, paint)
        return destBitmap
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap,为了避免图片太大，这里对图片进行了压缩
     * @param filePathWithName String
     * @return Bitmap?
     */
    @JvmStatic
    fun getBitmapDecodable(filePathWithName: String): Bitmap? {
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
            null
        }
    }

    /**
     * 堆叠Bitmap,最左边的在下面
     * @param bgSourceBitmap Bitmap
     * @param fgSourceBitmap Bitmap
     * @return Bitmap
     */
    @JvmStatic
    fun pileUpBitmap(bgSourceBitmap: Bitmap, fgSourceBitmap: Bitmap): Bitmap {
        val destBitmap = Bitmap.createBitmap(bgSourceBitmap.width, bgSourceBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(destBitmap)
        canvas.drawBitmap(bgSourceBitmap, 0f, 0f, null)
        canvas.drawBitmap(fgSourceBitmap, ((bgSourceBitmap.height - fgSourceBitmap.width) / 2).toFloat(), ((bgSourceBitmap.height - fgSourceBitmap.height) / 2).toFloat(), null)
        return destBitmap
    }
}