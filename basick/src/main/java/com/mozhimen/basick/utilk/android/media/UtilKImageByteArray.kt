package com.mozhimen.basick.utilk.android.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapFormat
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.io.asFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer

/**
 * @ClassName UtilKImageByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 17:45
 * @Version 1.0
 */
object UtilKImageByteArray {
    /**
     * yuv420转yuv420sp
     * @param y ByteArray
     * @param u ByteArray
     * @param v ByteArray
     * @param nv21 ByteArray
     * @param stride Int
     * @param height Int
     */
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

    /**
     * yuv422转yuv420sp
     * @param y ByteArray
     * @param u ByteArray
     * @param v ByteArray
     * @param nv21 ByteArray
     * @param stride Int
     * @param height Int
     */
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

    /**
     * nv21转位图 for camera1
     * @param nv21Bytes ByteArray
     * @param width Int
     * @param height Int
     * @return Bitmap
     */
    @JvmStatic
    @Throws(Exception::class)
    fun nv21Bytes2jpegBitmap(nv21Bytes: ByteArray, width: Int, height: Int): Bitmap {
        val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        val bytes: ByteArray?
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, byteArrayOutputStream)
            bytes = byteArrayOutputStream.toByteArray()
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            throw e
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
    }

    @JvmStatic
    fun nv21Bytes2jpegFile(nv21Bytes: ByteArray, width: Int, height: Int, filePathWithName: String): File? {
        return try {
            UtilKBitmapFormat.bitmap2jpegAlbumFile(nv21Bytes2jpegBitmap(nv21Bytes, width, height), filePathWithName)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(UtilKBitmapFormat.TAG, "nv21Bytes2JpegFile: ${e.message ?: ""}")
            null
        }
    }

    @JvmStatic
    fun grba8888Bytes2rgba8888Bitmap(grba8888Bytes: ByteArray, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(grba8888Bytes))
        return bitmap
    }

    /**
     * yuv流转Jpeg文件
     * @param nv21Bytes ByteArray
     * @param filePathWithName String
     * @param width Int
     * @param height Int
     */
    @JvmStatic
    fun nv21Bytes2jpegFile(nv21Bytes: ByteArray, filePathWithName: String, width: Int, height: Int, isOverwrite: Boolean = true): String {
        val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, byteArrayOutputStream)
            return byteArrayOutputStream.asFile(filePathWithName, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKBitmapFormat.TAG)
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
        return CMsg.WRONG
    }

    @JvmStatic
    @Throws(Exception::class)
    fun nv21Bytes2jpegBytes(nv21Bytes: ByteArray, width: Int, height: Int, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): ByteArray {
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
}