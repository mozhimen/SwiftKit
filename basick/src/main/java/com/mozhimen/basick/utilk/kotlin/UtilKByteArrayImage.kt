package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.byteArrayOutputStream2file
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.android.graphics.anyBytes2anyBitmap
import com.mozhimen.basick.utilk.android.graphics.jpegBitmap2jpegFile
import com.mozhimen.basick.utilk.java.io.byteArrayOutputStream2bytes

/**
 * @ClassName UtilKImageByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 17:45
 * @Version 1.0
 */
fun ByteArray.nv21Bytes2jpegBytes(width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
    UtilKByteArrayImage.nv21Bytes2jpegBytes(this, width, height, quality)

object UtilKByteArrayImage : IUtilK {

    @JvmStatic
    fun yuv420Bytes2yuv420spBytes(y: ByteArray, u: ByteArray, v: ByteArray, nv21: ByteArray, stride: Int, height: Int) {
        System.arraycopy(y, 0, nv21, 0, y.size)
        val length = y.size + u.size + v.size
        var uIndex = 0
        var vIndex = 0
        for (i in stride * height until length) {
            nv21[i] = v[vIndex++]
            nv21[i + 1] = u[uIndex++]
        }
    }

    @JvmStatic
    fun yuv422Bytes2yuv420spBytes(y: ByteArray, u: ByteArray, v: ByteArray, nv21: ByteArray, stride: Int, height: Int) {
        System.arraycopy(y, 0, nv21, 0, y.size)
        val length = y.size + u.size / 2 + v.size / 2
        var uIndex = 0
        var vIndex = 0
        var i = stride * height
        while (i < length) {
            nv21[i] = v[vIndex]
            nv21[i + 1] = u[uIndex]
            vIndex += 2
            uIndex += 2
            i += 2
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun nv21Bytes2jpegBytes(nv21Bytes: ByteArray, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
            yuvImage.compressToJpeg(Rect(0, 0, width, height), quality, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            throw e
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
    }

    @JvmStatic
    fun nv21Bytes2jpegFile(nv21Bytes: ByteArray, filePathWithName: String, width: Int, height: Int, isOverwrite: Boolean = true): File? {
        val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        return try {
            byteArrayOutputStream = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, byteArrayOutputStream)
            byteArrayOutputStream.byteArrayOutputStream2file(filePathWithName, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun nv21Bytes2jpegBitmap(nv21Bytes: ByteArray, width: Int, height: Int): Bitmap {
        val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, byteArrayOutputStream)
            return byteArrayOutputStream.byteArrayOutputStream2bytes().anyBytes2anyBitmap()
        } catch (e: Exception) {
            throw e
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun nv21Bytes2jpegFile(nv21Bytes: ByteArray, width: Int, height: Int, filePathWithName: String): File? =
        nv21Bytes2jpegBitmap(nv21Bytes, width, height).jpegBitmap2jpegFile(filePathWithName)

    @JvmStatic
    fun grba8888Bytes2rgba8888Bitmap(grba8888Bytes: ByteArray, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(grba8888Bytes))
        return bitmap
    }

    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * 裁剪nv21Bytes
     * @param nv21Bytes ByteArray 原始数据
     * @param width Int 原始图像的width
     * @param height Int 原始图像height
     * @param cropX Int 裁剪区域左上角的x
     * @param cropY Int 裁剪区域左上角的y
     * @param cropWidth Int 裁剪的宽度
     * @param cropHeight Int 裁剪的高度
     * @return ByteArray 裁剪后的图像数据
     */
    @JvmStatic
    fun clipNv21Bytes(nv21Bytes: ByteArray, width: Int, height: Int, cropX: Int, cropY: Int, cropWidth: Int, cropHeight: Int): ByteArray {
        // 目标区域取偶(YUV420SP要求图像高度是偶数)
        var left = cropX
        var top = cropY
        val begin = System.nanoTime()
        if (left % 2 == 1) {
            left--
        }
        if (top % 2 == 1) {
            top--
        }
        val bottom = top + cropHeight
        // 裁剪后的占用的大小
        val size = cropWidth * cropHeight * 3 / 2
        val data = ByteArray(size)
        // 按照YUV420SP格式，复制Y
        for (i in top until bottom) {
            System.arraycopy(nv21Bytes, left + i * width, data, (i - top) * cropWidth, cropWidth)
        }
        // 按照YUV420SP格式，复制UV
        val startH = height + top / 2
        val endH = height + bottom / 2
        for (i in startH until endH) {
            System.arraycopy(nv21Bytes, left + i * width, data, (i - startH + cropHeight) * cropWidth, cropWidth)
        }
        val end = System.nanoTime()
        return data
    }
}