package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import com.mozhimen.basick.utilk.commons.IUtilK
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.android.graphics.bitmapJpeg2fileJpeg
import com.mozhimen.basick.utilk.java.io.byteArrayOutputStream2bytes_use

/**
 * @ClassName UtilKImageByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 17:45
 * @Version 1.0
 */
fun ByteArray.bytesRgba88882bitmapRgba8888(width: Int, height: Int): Bitmap =
    UtilKByteArrayImage.bytesRgba88882bitmapRgba8888(this, width, height)

fun ByteArray.bytesNv212bytesJpeg(width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
    UtilKByteArrayImage.bytesNv212bytesJpeg(this, width, height, quality)

fun ByteArray.bytesNv212fileJpeg(strFilePathName: String, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100, isAppend: Boolean = false): File =
    UtilKByteArrayImage.bytesNv212fileJpeg(this, strFilePathName, width, height, quality, isAppend)

fun ByteArray.bytesNv212bitmapJpeg(width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): Bitmap =
    UtilKByteArrayImage.bytesNv212bitmapJpeg(this, width, height, quality)

fun ByteArray.bytesNv212fileJpeg2(strFilePathName: String, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKByteArrayImage.bytesNv212fileJpeg2(this, strFilePathName, width, height, quality)

////////////////////////////////////////////////////////////////////////////

object UtilKByteArrayImage : IUtilK {

    @JvmStatic
    fun bytesYuv4202bytesYuv420sp(bytesY: ByteArray, bytesU: ByteArray, bytesV: ByteArray, bytesNV21: ByteArray, stride: Int, height: Int) {
        System.arraycopy(bytesY, 0, bytesNV21, 0, bytesY.size)
        val length = bytesY.size + bytesU.size + bytesV.size
        var uIndex = 0
        var vIndex = 0
        for (i in stride * height until length) {
            bytesNV21[i] = bytesV[vIndex++]
            bytesNV21[i + 1] = bytesU[uIndex++]
        }
    }

    @JvmStatic
    fun bytesYuv4222bytesYuv420sp(bytesY: ByteArray, bytesU: ByteArray, bytesV: ByteArray, bytesNV21: ByteArray, stride: Int, height: Int) {
        System.arraycopy(bytesY, 0, bytesNV21, 0, bytesY.size)
        val length = bytesY.size + bytesU.size / 2 + bytesV.size / 2
        var uIndex = 0
        var vIndex = 0
        var i = stride * height
        while (i < length) {
            bytesNV21[i] = bytesV[vIndex]
            bytesNV21[i + 1] = bytesU[uIndex]
            vIndex += 2
            uIndex += 2
            i += 2
        }
    }

    @JvmStatic
    fun bytesRgba88882bitmapRgba8888(bytesRgba8888: ByteArray, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bytesRgba8888))
        return bitmap
    }

    @JvmStatic
    fun bytesNv212bytesJpeg(bytesNv21: ByteArray, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val yuvImage = YuvImage(bytesNv21, ImageFormat.NV21, width, height, null)
        yuvImage.compressToJpeg(Rect(0, 0, width, height), quality, byteArrayOutputStream)
        return byteArrayOutputStream.byteArrayOutputStream2bytes_use()
    }

    @JvmStatic
    fun bytesNv212fileJpeg(bytesNv21: ByteArray, strFilePathName: String, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100, isAppend: Boolean = false): File =
        bytesNv212bytesJpeg(bytesNv21, width, height, quality).bytes2file(strFilePathName, isAppend)

    @JvmStatic
    @Throws(Exception::class)
    fun bytesNv212bitmapJpeg(bytesNv21: ByteArray, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): Bitmap =
        bytesNv212bytesJpeg(bytesNv21, width, height, quality).bytes2bitmapAny()

    @JvmStatic
    @Throws(Exception::class)
    fun bytesNv212fileJpeg2(bytesNv21: ByteArray, strFilePathName: String, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        bytesNv212bitmapJpeg(bytesNv21, width, height, quality).bitmapJpeg2fileJpeg(strFilePathName)

    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * 裁剪nv21Bytes
     * @param bytesNv21 ByteArray 原始数据
     * @param width Int 原始图像的width
     * @param height Int 原始图像height
     * @param cropX Int 裁剪区域左上角的x
     * @param cropY Int 裁剪区域左上角的y
     * @param cropWidth Int 裁剪的宽度
     * @param cropHeight Int 裁剪的高度
     * @return ByteArray 裁剪后的图像数据
     */
    @JvmStatic
    fun clipNv21Bytes(bytesNv21: ByteArray, width: Int, height: Int, cropX: Int, cropY: Int, cropWidth: Int, cropHeight: Int): ByteArray {
        // 目标区域取偶(YUV420SP要求图像高度是偶数)
        var left = cropX
        var top = cropY
//        val begin = System.nanoTime()
        if (left % 2 == 1)
            left--
        if (top % 2 == 1)
            top--
        val bottom = top + cropHeight
        // 裁剪后的占用的大小
        val size = cropWidth * cropHeight * 3 / 2
        val data = ByteArray(size)
        // 按照YUV420SP格式，复制Y
        for (i in top until bottom)
            System.arraycopy(bytesNv21, left + i * width, data, (i - top) * cropWidth, cropWidth)
        // 按照YUV420SP格式，复制UV
        val startH = height + top / 2
        val endH = height + bottom / 2
        for (i in startH until endH)
            System.arraycopy(bytesNv21, left + i * width, data, (i - startH + cropHeight) * cropWidth, cropWidth)
//        val end = System.nanoTime()
        return data
    }
}