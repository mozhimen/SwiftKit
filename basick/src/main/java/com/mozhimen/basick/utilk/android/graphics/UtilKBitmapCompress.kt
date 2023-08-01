package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.util.Log
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKString
import kotlin.math.roundToInt
import kotlin.math.sqrt


/**
 * @ClassName UtilKBitmapCompress
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/4 14:35
 * @Version 1.0
 */
fun String.getCompressFormat(): CompressFormat =
    UtilKBitmapCompress.getCompressFormat(this)

fun Bitmap.anyBitmapCompressScaled(@androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap =
    UtilKBitmapCompress.anyBitmapCompressScaled(this, quality)

object UtilKBitmapCompress : BaseUtilK() {

    /**
     * 压缩质量
     * @param sourceBitmap Bitmap
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun anyBitmapCompressQuality(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int = 50): Bitmap? {
        val bytes: ByteArray = UtilKBitmapFormat.anyBitmap2anyBytes(sourceBitmap, compressFormat, quality) ?: return null
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also { printBitmapInfo(it, bytes, quality) }
    }

    /**
     * 压缩采样率
     * @param bitmapPathWithName String
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun anyBitmapCompressSampleSize(bitmapPathWithName: String, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = (100f / quality.toFloat()).roundToInt().also { Log.v(TAG, "compressSampleSize: inSampleSize $it") }
        return BitmapFactory.decodeFile(bitmapPathWithName, options).also { printBitmapInfo(it, null, quality) }
    }

    /**
     * 缩放压缩法
     * @param sourceBitmap Bitmap
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun anyBitmapCompressMatrix(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
        val ratio: Float = sqrt(quality.toFloat() / 100f).also { Log.v(TAG, "compressMatrix: ratio $it") }//这里很好理解, 我们是对面的比例, 开方才是边的缩小比例
        return UtilKBitmapDeal.anyBitmapScaleRatio(sourceBitmap, ratio).also { printBitmapInfo(it, null, quality) }
    }

    /**
     * rgb565压缩方法
     * @param sourceBitmap Bitmap
     * @return Bitmap
     */
    @JvmStatic
    fun anyBitmapCompress2rgb565Bitmap(sourceBitmap: Bitmap): Bitmap {
        return UtilKBitmapFormat.anyBitmap2rgb565Bitmap(sourceBitmap).also { printBitmapInfo(it, null, 100) }
    }

    /**
     * rgb565压缩方法
     * @param bitmapPathWithName String
     */
    @JvmStatic
    fun pathBitmapCompress2rgb565Bitmap(bitmapPathWithName: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(bitmapPathWithName, options).also { printBitmapInfo(it, null, 100) }
    }

    /**
     * compressScaledBitmap方法压缩
     * @param sourceBitmap Bitmap
     */
    @JvmStatic
    fun anyBitmapCompressScaled(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
        val ratio: Float = sqrt(quality.toFloat() / 100f).also { Log.d(TAG, "compressScaledBitmap: ratio $it") }//这里很好理解, 我们是对面的比例, 开方才是边的缩小比例
        return UtilKBitmapDeal.anyBitmapResize(sourceBitmap, (sourceBitmap.width * ratio).toInt(), (sourceBitmap.height * ratio).toInt()).also { printBitmapInfo(it, null, quality) }
    }

    @JvmStatic
    fun getCompressFormat(compressFormatStr: String): CompressFormat =
        when (UtilKString.getFilenameExtension(compressFormatStr).lowercase()) {
            "png" -> CompressFormat.PNG
            "webp" -> CompressFormat.WEBP
            else -> CompressFormat.JPEG
        }

    /**
     * 打印bitmap信息
     * @param bitmap Bitmap
     * @param bytes ByteArray?
     * @param quality Int
     */
    @JvmStatic
    private fun printBitmapInfo(bitmap: Bitmap, bytes: ByteArray?, quality: Int) {
        Log.v(
            TAG,
            "compress after bitmap size: ${bitmap.byteCount / 1024 / 1024}MB width: ${bitmap.width} height: ${bitmap.height} bytes.length: ${bytes?.let { it.size / 1024 } ?: 0}KB quality: $quality"
        )
    }
}