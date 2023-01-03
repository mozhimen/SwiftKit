package com.mozhimen.componentk.cameraxk.helpers

import android.graphics.*
import android.media.Image.Plane
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.mozhimen.underlayk.logk.LogK
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


/**
 * @ClassName ImageConverter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/10 18:46
 * @Version 1.0
 */
object ImageConverter {

    private const val TAG = "ImageConverter>>>>>"

    /**
     * rgba8888转Bitmap
     * @param imageProxy ImageProxy
     * @return Bitmap
     */
    @JvmStatic
    fun rgba8888Image2Rgba8888Bitmap(imageProxy: ImageProxy): Bitmap {
        val bitmap = Bitmap.createBitmap(imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888)
        // Copy out RGB bits to the shared bitmap buffer
        imageProxy.use { bitmap.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }
        return bitmap
    }

    /**
     * 将来自 CameraX API 的 YUV_420_888 图像转换为Bitmap
     * @param imageProxy ImageProxy?
     * @return Bitmap?
     */
    @JvmStatic
    @ExperimentalGetImage
    fun yuv420888Image2JpegBitmap(imageProxy: ImageProxy): Bitmap? {
        val nv21Bytes = yuv420888Planes2Nv21Bytes(imageProxy.image!!.planes, imageProxy.width, imageProxy.height)
        return nv21Bytes2JpegBitmap(ByteBuffer.wrap(nv21Bytes), imageProxy.width, imageProxy.height)
    }

    /**
     * 将来自 CameraX API 的 JPEG 图像转换为Bitmap
     * @param imageProxy ImageProxy
     * @return Bitmap
     */
    @JvmStatic
    fun jpegImage2JpegBitmap(imageProxy: ImageProxy): Bitmap {
        val buffer = imageProxy.planes[0].buffer
        val size = buffer.remaining()
        val jpegBytes = ByteArray(size)
        buffer.get(jpegBytes, 0, size)
        return BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.size)
    }

    /**
     * YUV_420_888转NV21
     * @param imageProxy ImageProxy
     * @return ByteArray
     */
    @JvmStatic
    @ExperimentalGetImage
    fun yuv4208882Nv21Bytes(imageProxy: ImageProxy): ByteArray {
        return yuv420888Planes2Nv21Bytes(imageProxy.image!!.planes, imageProxy.width, imageProxy.height)
    }

    /**
     * 将 NV21 格式字节缓冲区转换为Bitmap
     * @param nv21Bytes ByteBuffer
     * @param width Int
     * @param height Int
     * @return Bitmap?
     */
    @JvmStatic
    fun nv21Bytes2JpegBitmap(nv21Bytes: ByteBuffer, width: Int, height: Int): Bitmap? {
        nv21Bytes.rewind()
        val imageInBuffer = ByteArray(nv21Bytes.limit())
        nv21Bytes[imageInBuffer, 0, imageInBuffer.size]
        val byteArrayOutputStream = ByteArrayOutputStream()
        try {
            val yuvImage = YuvImage(imageInBuffer, ImageFormat.NV21, width, height, null)
            yuvImage.compressToJpeg(Rect(0, 0, width, height), 80, byteArrayOutputStream)
            return BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size())
        } catch (e: Exception) {
            e.printStackTrace()
            LogK.et(TAG, "buffer2Bitmap Exception ${e.message}")
        } finally {
            byteArrayOutputStream.close()
        }
        return null
    }

    @JvmStatic
    fun yuv420888Planes2Nv21Bytes(yuv420888Planes: Array<Plane>, width: Int, height: Int): ByteArray {
        val imageSize = width * height
        val outBytes = ByteArray(imageSize + 2 * (imageSize / 4))
        if (isYuv420888PlanesNv21(yuv420888Planes, width, height)) {
            yuv420888Planes[0].buffer[outBytes, 0, imageSize]// 复制 Y 的值
            yuv420888Planes[2].buffer[outBytes, imageSize, 1]// 从 V 缓冲区获取第一个 V 值，因为 U 缓冲区不包含它
            yuv420888Planes[1].buffer[outBytes, imageSize + 1, 2 * imageSize / 4 - 1]// 从 U 缓冲区复制第一个 U 值和剩余的 VU 值
        } else {
            // 回退到一个一个地复制 UV 值，这更慢但也有效
            unpackYuv420888Plane(yuv420888Planes[0], width, height, outBytes, 0, 1)// 取 Y.
            unpackYuv420888Plane(yuv420888Planes[1], width, height, outBytes, imageSize + 1, 2)// 取 U.
            unpackYuv420888Plane(yuv420888Planes[2], width, height, outBytes, imageSize, 2)// 取 V.
        }
        return outBytes
    }

    /**
     * 检查 YUV_420_888 图像的 UV平面缓冲区是否为 NV21 格式
     * @param planes Array<Plane>
     * @param width Int
     * @param height Int
     * @return Boolean
     */
    @JvmStatic
    private fun isYuv420888PlanesNv21(planes: Array<Plane>, width: Int, height: Int): Boolean {
        val imageSize = width * height
        val uBuffer = planes[1].buffer
        val vBuffer = planes[2].buffer

        // 备份缓冲区属性
        val vBufferPosition = vBuffer.position()
        val uBufferLimit = uBuffer.limit()

        // 将 V 缓冲区推进 1 个字节，因为 U 缓冲区将不包含第一个 V 值
        vBuffer.position(vBufferPosition + 1)
        // 切掉 U 缓冲区的最后一个字节，因为 V 缓冲区将不包含最后一个 U 值
        uBuffer.limit(uBufferLimit - 1)

        // 检查缓冲区是否相等并具有预期的元素数量
        val areNV21 = vBuffer.remaining() == 2 * imageSize / 4 - 2 && vBuffer.compareTo(uBuffer) == 0

        // 将缓冲区恢复到初始状态。
        vBuffer.position(vBufferPosition)
        uBuffer.limit(uBufferLimit)
        return areNV21
    }

    /**
     * 将图像平面解压缩为字节数组
     * 输入平面数据将被复制到“out”中，从“offset”开始，每个像素将被“pixelStride”隔开。请注意，输出上没有行填充
     * @param plane Plane
     * @param width Int
     * @param height Int
     * @param out ByteArray
     * @param offset Int
     * @param pixelStride Int
     */
    @JvmStatic
    private fun unpackYuv420888Plane(plane: Plane, width: Int, height: Int, out: ByteArray, offset: Int, pixelStride: Int) {
        val buffer = plane.buffer
        buffer.rewind()

        // 计算当前平面的大小。假设它的纵横比与原始图像相同
        val numRow = (buffer.limit() + plane.rowStride - 1) / plane.rowStride
        if (numRow == 0) {
            return
        }
        val scaleFactor = height / numRow
        val numCol = width / scaleFactor

        // 提取输出缓冲区中的数据
        var outputPos = offset
        var rowStart = 0
        for (row in 0 until numRow) {
            var inputPos = rowStart
            for (col in 0 until numCol) {
                out[outputPos] = buffer[inputPos]
                outputPos += pixelStride
                inputPos += plane.pixelStride
            }
            rowStart += plane.rowStride
        }
    }
}