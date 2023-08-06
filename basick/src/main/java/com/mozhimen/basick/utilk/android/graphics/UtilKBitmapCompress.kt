package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import com.mozhimen.basick.utilk.android.util.vt
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
fun String.strCompressFormat2compressFormat(): CompressFormat =
    UtilKBitmapCompress.strCompressFormat2compressFormat(this)

fun Bitmap.compressAnyBitmapQuality(compressFormat: CompressFormat = CompressFormat.JPEG, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int = 50): Bitmap? =
    UtilKBitmapCompress.compressAnyBitmapQuality(this, compressFormat, quality)

fun String.compressStrBitmapPathSampleSize(@androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap? =
    UtilKBitmapCompress.compressStrBitmapPathSampleSize(this, quality)

fun Bitmap.compressAnyBitmapMatrix(@androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap =
    UtilKBitmapCompress.compressAnyBitmapMatrix(this, quality)

fun Bitmap.compressAnyBitmap2rgb565Bitmap(): Bitmap =
    UtilKBitmapCompress.compressAnyBitmap2rgb565Bitmap(this)

fun String.compressStrBitmapPath2rgb565Bitmap(): Bitmap? =
    UtilKBitmapCompress.compressStrBitmapPath2rgb565Bitmap(this)

fun Bitmap.compressAnyBitmapScaled(@androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap =
    UtilKBitmapCompress.compressAnyBitmapScaled(this, quality)

object UtilKBitmapCompress : BaseUtilK() {

    @JvmStatic
    fun strCompressFormat2compressFormat(strCompressFormat: String): CompressFormat =
        when (UtilKString.getFilenameExtension(strCompressFormat).lowercase()) {
            "png" -> CompressFormat.PNG
            "webp" -> CompressFormat.WEBP
            else -> CompressFormat.JPEG
        }

    /**
     * 压缩质量
     * @param sourceBitmap Bitmap
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun compressAnyBitmapQuality(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int = 50): Bitmap? =
        sourceBitmap.anyBitmap2anyBytes(compressFormat, quality)?.let { bytes ->
            bytes.anyBytes2anyBitmap().also { printBitmapInfo(it, bytes, quality) }
        }

    /**
     * 压缩采样率
     * @param bitmapPathWithName String
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun compressStrBitmapPathSampleSize(bitmapPathWithName: String, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inSampleSize = (100f / quality.toFloat()).roundToInt().also { "compressSampleSize: inSampleSize $it".vt(TAG) }
        return bitmapPathWithName.strFilePath2anyBitmap(options)?.also { printBitmapInfo(it, null, quality) }
    }

    /**
     * 缩放压缩法
     * @param sourceBitmap Bitmap
     * @param quality Int
     * @return Bitmap
     */
    @JvmStatic
    fun compressAnyBitmapMatrix(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
        val ratio: Float = sqrt(quality.toFloat() / 100f).also { "compressMatrix: ratio $it".vt(TAG) }//这里很好理解, 我们是对面的比例, 开方才是边的缩小比例
        return sourceBitmap.applyAnyBitmapScaleRatio(ratio).also { printBitmapInfo(it, null, quality) }
    }

    /**
     * rgb565压缩方法
     * @param sourceBitmap Bitmap
     * @return Bitmap
     */
    @JvmStatic
    fun compressAnyBitmap2rgb565Bitmap(sourceBitmap: Bitmap): Bitmap =
        sourceBitmap.anyBitmap2rgb565Bitmap().also { printBitmapInfo(it, null, 100) }

    /**
     * rgb565压缩方法
     * @param bitmapPathWithName String
     */
    @JvmStatic
    fun compressStrBitmapPath2rgb565Bitmap(bitmapPathWithName: String): Bitmap? {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return bitmapPathWithName.strFilePath2anyBitmap(options)?.also { printBitmapInfo(it, null, 100) }
    }

    /**
     * compressScaledBitmap方法压缩
     * @param sourceBitmap Bitmap
     */
    @JvmStatic
    fun compressAnyBitmapScaled(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 1, to = 100) quality: Int): Bitmap {
        val ratio: Float = sqrt(quality.toFloat() / 100f).also { "compressScaledBitmap: ratio $it".vt(TAG) }//这里很好理解, 我们是对面的比例, 开方才是边的缩小比例
        return sourceBitmap.applyAnyBitmapResize((sourceBitmap.width * ratio).toInt(), (sourceBitmap.height * ratio).toInt()).also { printBitmapInfo(it, null, quality) }
    }

    /**
     * 打印bitmap信息
     * @param bitmap Bitmap
     * @param bytes ByteArray?
     * @param quality Int
     */
    @JvmStatic
    private fun printBitmapInfo(bitmap: Bitmap, bytes: ByteArray?, quality: Int) {
        "compress after bitmap size: ${bitmap.byteCount / 1024 / 1024}MB width: ${bitmap.width} height: ${bitmap.height} bytes.length: ${bytes?.let { it.size / 1024 } ?: 0}KB quality: $quality".vt(TAG)
    }
}