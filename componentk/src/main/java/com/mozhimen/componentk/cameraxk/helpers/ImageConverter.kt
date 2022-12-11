package com.mozhimen.componentk.cameraxk.helpers

import android.annotation.SuppressLint
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
     * rgb8888转Bitmap
     * @param image ImageProxy
     * @return Bitmap
     */
    @JvmStatic
    fun rgb2Bitmap(image: ImageProxy): Bitmap {
        val bitmapBuffer = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        // Copy out RGB bits to the shared bitmap buffer
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        return bitmapBuffer
    }

    /**
     * 将来自 CameraX API 的 YUV_420_888 图像转换为Bitmap
     * @param imageProxy ImageProxy?
     * @return Bitmap?
     */
    @JvmStatic
    @ExperimentalGetImage
    fun yuv2Bitmap(imageProxy: ImageProxy): Bitmap? {
        if (imageProxy.image == null) return null
        val nv21Buffer = yuv420ThreePlanesToNV21(imageProxy.image!!.planes, imageProxy.width, imageProxy.height)
        return buffer2Bitmap(ByteBuffer.wrap(nv21Buffer), imageProxy.width, imageProxy.height)
    }

    /**
     * YUV_420_888转NV21
     * @param imageProxy ImageProxy
     * @return ByteArray
     */
    @JvmStatic
    @ExperimentalGetImage
    fun yuv2nv21(imageProxy: ImageProxy): ByteArray? {
        if (imageProxy.image == null) return null
        return yuv420ThreePlanesToNV21(imageProxy.image!!.planes, imageProxy.width, imageProxy.height)
    }

    /**
     * 将 NV21 格式字节缓冲区转换为Bitmap
     * @param data ByteBuffer
     * @param width Int
     * @param height Int
     * @return Bitmap?
     */
    @JvmStatic
    fun buffer2Bitmap(data: ByteBuffer, width: Int, height: Int): Bitmap? {
        data.rewind()
        val imageInBuffer = ByteArray(data.limit())
        data[imageInBuffer, 0, imageInBuffer.size]
        try {
            val image = YuvImage(imageInBuffer, ImageFormat.NV21, width, height, null)
            val stream = ByteArrayOutputStream()
            image.compressToJpeg(Rect(0, 0, width, height), 80, stream)
            val bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size())
            stream.close()
            return bmp
        } catch (e: Exception) {
            LogK.et(TAG, "buffer2Bitmap Exception ${e.message}")
        }
        return null
    }

    @JvmStatic
    fun jpeg2Bitmap(image: ImageProxy): Bitmap {
        val planes: Array<ImageProxy.PlaneProxy> = image.planes
        val buffer = planes[0].buffer
        val size = buffer.remaining()
        val jpeg = ByteArray(size)
        buffer.get(jpeg, 0, size)
        return BitmapFactory.decodeByteArray(jpeg, 0, jpeg.size)
    }

    @JvmStatic
    fun yuv420ThreePlanesToNV21(yuv420_888_planes: Array<Plane>, width: Int, height: Int): ByteArray {
        val imageSize = width * height
        val out = ByteArray(imageSize + 2 * (imageSize / 4))
        if (isUVPlanesNV21(yuv420_888_planes, width, height)) {
            yuv420_888_planes[0].buffer[out, 0, imageSize]// 复制 Y 的值
            yuv420_888_planes[2].buffer[out, imageSize, 1]// 从 V 缓冲区获取第一个 V 值，因为 U 缓冲区不包含它
            yuv420_888_planes[1].buffer[out, imageSize + 1, 2 * imageSize / 4 - 1]// 从 U 缓冲区复制第一个 U 值和剩余的 VU 值
        } else {
            // 回退到一个一个地复制 UV 值，这更慢但也有效
            unpackPlane(yuv420_888_planes[0], width, height, out, 0, 1)// 取 Y.
            unpackPlane(yuv420_888_planes[1], width, height, out, imageSize + 1, 2)// 取 U.
            unpackPlane(yuv420_888_planes[2], width, height, out, imageSize, 2)// 取 V.
        }
        return out
    }

    /**
     * 检查 YUV_420_888 图像的 UV平面缓冲区是否为 NV21 格式
     * @param planes Array<Plane>
     * @param width Int
     * @param height Int
     * @return Boolean
     */
    @JvmStatic
    private fun isUVPlanesNV21(planes: Array<Plane>, width: Int, height: Int): Boolean {
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
    private fun unpackPlane(plane: Plane, width: Int, height: Int, out: ByteArray, offset: Int, pixelStride: Int) {
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