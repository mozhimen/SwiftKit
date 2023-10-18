package com.mozhimen.basick.utilk.android.media

import android.graphics.ImageFormat
import android.media.Image
import com.mozhimen.basick.utilk.kotlin.bytesNv212bytesJpeg

/**
 * @ClassName UtilKImage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 17:40
 * @Version 1.0
 */
fun Image.anyImage2jpegBytes(): ByteArray =
    UtilKImageFormat.anyImage2jpegBytes(this)

fun Image.yuv420888Image2nv21Bytes(): ByteArray =
    UtilKImageFormat.yuv420888Image2nv21Bytes(this)

fun Image.yuv420888Image2jpegBytes(): ByteArray =
    UtilKImageFormat.yuv420888Image2jpegBytes(this)

fun Image.jpegImage2jpegBytes(): ByteArray =
    UtilKImageFormat.jpegImage2jpegBytes(this)

object UtilKImageFormat {
    @JvmStatic
    @Throws(Exception::class)
    fun anyImage2jpegBytes(image: Image): ByteArray {
        val bytes: ByteArray = when (image.format) {
            ImageFormat.JPEG -> jpegImage2jpegBytes(image)
            ImageFormat.YUV_420_888 -> yuv420888Image2jpegBytes(image)
            else -> throw Exception("cannot handle this format")
        }
        return bytes
    }

    @JvmStatic
    fun yuv420888Image2nv21Bytes(image: Image): ByteArray {
        val nv21Bytes: ByteArray
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer
        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()
        nv21Bytes = ByteArray(ySize + uSize + vSize)

        //U and V are swapped
        yBuffer[nv21Bytes, 0, ySize]
        vBuffer[nv21Bytes, ySize, vSize]
        uBuffer[nv21Bytes, ySize + vSize, uSize]
        return nv21Bytes
    }

    @JvmStatic
    fun yuv420888Image2jpegBytes(image: Image): ByteArray =
        image.yuv420888Image2nv21Bytes().bytesNv212bytesJpeg(image.width, image.height)

    @JvmStatic
    fun jpegImage2jpegBytes(image: Image): ByteArray {
        val bytes: ByteArray
        val buffer = image.planes[0].buffer
        bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return bytes
    }
}