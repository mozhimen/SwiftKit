package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.annotation.IntRange
import java.io.OutputStream


/**
 * @ClassName UtilKBitmap
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 14:21
 * @Version 1.0
 */
fun Bitmap.getByteCount_ofM(): Int =
    UtilKBitmap.getByteCount_ofM(this)

object UtilKBitmap {
    @JvmStatic
    fun getByteCount(bitmap: Bitmap): Int =
        bitmap.byteCount

    @JvmStatic
    fun getByteCount_ofM(bitmap: Bitmap): Int =
        getByteCount(bitmap) / 1024 / 1024

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun compress(bitmap: Bitmap, format: Bitmap.CompressFormat, @IntRange(from = 0, to = 100) quality: Int, outputStream: OutputStream) {
        bitmap.compress(format, quality, outputStream)
    }

    @JvmStatic
    fun createScaledBitmap(bitmapSource: Bitmap, destWidth: Int, heightDest: Int, filter: Boolean): Bitmap =
        Bitmap.createScaledBitmap(bitmapSource, destWidth, heightDest, filter)

    @JvmStatic
    fun createBitmap(width: Int, height: Int, config: Bitmap.Config): Bitmap =
        Bitmap.createBitmap(width, height, config)

    @JvmStatic
    fun createBitmap(bitmapSource: Bitmap, x: Int, y: Int, width: Int, height: Int, m: Matrix?, filter: Boolean): Bitmap =
        Bitmap.createBitmap(bitmapSource, x, y, width, height, m, filter)
}