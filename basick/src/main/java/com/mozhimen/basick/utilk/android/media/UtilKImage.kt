package com.mozhimen.basick.utilk.android.media

import android.graphics.ImageFormat
import android.media.Image

/**
 * @ClassName UtilKImage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 17:40
 * @Version 1.0
 */
object UtilKImage {
    /**
     * image转Bytes
     * @param image Image
     * @return ByteArray
     */
    @JvmStatic
    @Throws(Exception::class)
    fun image2jpegBytes(image: Image): ByteArray {
        val bytes: ByteArray = when (image.format) {
            ImageFormat.JPEG -> {
                jpegImage2jpegBytes(image)
            }

            ImageFormat.YUV_420_888 -> {
                yuv420888Image2jpegBytes(image)
            }

            else -> {
                throw Exception("cannot handle this format")
            }
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

    /**
     * yuv420888Image转JpegBytes
     * @param image Image
     * @return ByteArray
     */
    @JvmStatic
    fun yuv420888Image2jpegBytes(image: Image): ByteArray {
        return UtilKImageByteArray.nv21Bytes2jpegBytes(yuv420888Image2nv21Bytes(image), image.width, image.height)
    }

    @JvmStatic
    fun jpegImage2jpegBytes(image: Image): ByteArray {
        val bytes: ByteArray
        val buffer = image.planes[0].buffer
        bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return bytes
    }
}