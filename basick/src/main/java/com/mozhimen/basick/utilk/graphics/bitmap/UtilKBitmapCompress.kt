package com.mozhimen.basick.utilk.graphics.bitmap

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.util.Log
import com.mozhimen.basick.utilk.exts.et
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt
import kotlin.math.sqrt


/**
 * @ClassName UtilKBitmapCompress
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/4 14:35
 * @Version 1.0
 */
object UtilKBitmapCompress {
    private const val TAG = "UtilKBitmapCompress>>>>>"

    /**
     * 压缩质量
     * @param sourceBitmap Bitmap
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun compressQuality(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int = 50): Bitmap? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        try {
            sourceBitmap.compress(CompressFormat.JPEG, quality, byteArrayOutputStream)
            val bytes: ByteArray = byteArrayOutputStream.toByteArray()
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also { printBitmapInfo(it, bytes, quality) }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
        }
        return null
    }

    /**
     * 压缩采样率
     * @param bitmapPathWithName String
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun compressSampleSize(bitmapPathWithName: String, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
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
    fun compressMatrix(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
        val ratio: Float = sqrt(quality.toFloat() / 100f).also { Log.v(TAG, "compressMatrix: ratio $it") }//这里很好理解, 我们是对面的比例, 开方才是边的缩小比例
        return UtilKBitmapDeal.scaleBitmap(sourceBitmap, ratio).also { printBitmapInfo(it, null, quality) }
    }

    /**
     * rgb565压缩方法
     * @param sourceBitmap Bitmap
     * @return Bitmap
     */
    @JvmStatic
    fun compressRgb565(sourceBitmap: Bitmap): Bitmap {
        return UtilKBitmapFormat.bitmap2Rgb565Bitmap(sourceBitmap).also { printBitmapInfo(it, null, 100) }
    }

    /**
     * rgb565压缩方法
     * @param filePathWithName String
     */
    @JvmStatic
    fun compressRgb565(filePathWithName: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(filePathWithName, options).also { printBitmapInfo(it, null, 100) }
    }

    /**
     * compressScaledBitmap方法压缩
     * @param sourceBitmap Bitmap
     */
    @JvmStatic
    fun compressScaledBitmap(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
        val ratio: Float = sqrt(quality.toFloat() / 100f).also { Log.d(TAG, "compressScaledBitmap: ratio $it") }//这里很好理解, 我们是对面的比例, 开方才是边的缩小比例
        return UtilKBitmapDeal.resizeBitmap(sourceBitmap, (sourceBitmap.width * ratio).toInt(), (sourceBitmap.height * ratio).toInt()).also { printBitmapInfo(it, null, quality) }
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