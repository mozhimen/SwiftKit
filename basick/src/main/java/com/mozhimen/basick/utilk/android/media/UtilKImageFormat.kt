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
fun Image.imageAny2bytesJpeg(): ByteArray =
    UtilKImageFormat.imageAny2bytesJpeg(this)

fun Image.imageYuv4208882bytesNv21(): ByteArray =
    UtilKImageFormat.imageYuv4208882bytesNv21(this)

fun Image.imageYuv4208882bytesJpeg(): ByteArray =
    UtilKImageFormat.imageYuv4208882bytesJpeg(this)

fun Image.imageJpeg2bytesJpeg(): ByteArray =
    UtilKImageFormat.imageJpeg2bytesJpeg(this)

object UtilKImageFormat {
    @JvmStatic
    @Throws(Exception::class)
    fun imageAny2bytesJpeg(image: Image): ByteArray {
        val bytes: ByteArray = when (image.format) {
            ImageFormat.JPEG -> imageJpeg2bytesJpeg(image)
            ImageFormat.YUV_420_888 -> imageYuv4208882bytesJpeg(image)
            else -> throw Exception("cannot handle this format")
        }
        return bytes
    }

    @JvmStatic
    fun imageYuv4208882bytesNv21(image: Image): ByteArray {
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
    fun imageYuv4208882bytesJpeg(image: Image): ByteArray =
        image.imageYuv4208882bytesNv21().bytesNv212bytesJpeg(image.width, image.height)

    @JvmStatic
    fun imageJpeg2bytesJpeg(image: Image): ByteArray {
        val bytes: ByteArray
        val buffer = image.planes[0].buffer
        bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return bytes
    }
}