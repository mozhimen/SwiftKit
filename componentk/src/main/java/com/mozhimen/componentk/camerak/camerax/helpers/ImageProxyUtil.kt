package com.mozhimen.componentk.camerak.camerax.helpers

import android.annotation.SuppressLint
import android.graphics.*
import android.media.Image.Plane
import android.util.Log
import androidx.annotation.IntRange
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.mozhimen.basick.lintk.optin.OptInFieldCall_Close
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.bytes2bitmapAny
import com.mozhimen.basick.utilk.kotlin.bytesNv212bitmapJpeg
import java.nio.ByteBuffer


/**
 * @ClassName ImageConverter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/10 18:46
 * @Version 1.0
 */
fun ImageProxy.rgba8888ImageProxy2Rgba8888Bitmap(): Bitmap =
    ImageProxyUtil.rgba8888ImageProxy2Rgba8888Bitmap(this)

@ExperimentalGetImage
@OptInFieldCall_Close
fun ImageProxy.yuv420888ImageProxy2JpegBitmap(): Bitmap =
    ImageProxyUtil.yuv420888ImageProxy2JpegBitmap(this)

fun ImageProxy.jpegImageProxy2JpegBitmap(): Bitmap =
    ImageProxyUtil.jpegImageProxy2JpegBitmap(this)

object ImageProxyUtil : BaseUtilK() {

    /**
     * rgba8888转Bitmap
     */
    @SuppressLint("UnsafeOptInUsageError")
    @JvmStatic
    fun rgba8888ImageProxy2Rgba8888Bitmap(imageProxy: ImageProxy): Bitmap {
        Log.v(TAG, "rgba8888Image2Rgba8888Bitmap: imageProxy width ${imageProxy.width} height ${imageProxy.height}")
        val bitmap = Bitmap.createBitmap(imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888)
        // Copy out RGB bits to the shared bitmap buffer
        imageProxy.use { bitmap.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }
        return bitmap
    }

    /**
     * 将来自 CameraX API 的 YUV_420_888 图像转换为Bitmap
     */
    @JvmStatic
    @ExperimentalGetImage
    @OptInFieldCall_Close
    fun yuv420888ImageProxy2JpegBitmap(imageProxy: ImageProxy): Bitmap {
        val nv21Bytes = yuv420888Planes2Nv21Bytes(imageProxy.image!!.planes, imageProxy.width, imageProxy.height)
        return nv21Buffer2JpegBitmap(ByteBuffer.wrap(nv21Bytes), imageProxy.width, imageProxy.height)
    }

    /**
     * 将来自 CameraX API 的 JPEG 图像转换为Bitmap
     */
    @JvmStatic
    fun jpegImageProxy2JpegBitmap(imageProxy: ImageProxy): Bitmap {
        val buffer = imageProxy.planes[0].buffer
        val size = buffer.remaining()
        val jpegBytes = ByteArray(size)
        buffer.get(jpegBytes, 0, size)
        return jpegBytes.bytes2bitmapAny()
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * YUV_420_888转NV21
     */
    @JvmStatic
    @ExperimentalGetImage
    fun yuv420888ImageProxy2Nv21Bytes(imageProxy: ImageProxy): ByteArray =
        yuv420888Planes2Nv21Bytes(imageProxy.image!!.planes, imageProxy.width, imageProxy.height)

    /**
     * 将 NV21 格式字节缓冲区转换为Bitmap
     */
    @JvmStatic
    fun nv21Buffer2JpegBitmap(nv21Buffer: ByteBuffer, width: Int, height: Int, @IntRange(from = 0, to = 100) quality: Int = 100): Bitmap {
        nv21Buffer.rewind()
        val nv21Bytes = ByteArray(nv21Buffer.limit())
        nv21Buffer[nv21Bytes, 0, nv21Bytes.size]
        return nv21Bytes.bytesNv212bitmapJpeg(width, height, quality)
    }

    @JvmStatic
    fun yuv420888Planes2Nv21Bytes(yuv420888Planes: Array<Plane>, width: Int, height: Int): ByteArray {
        val imageSize = width * height
        val nv21Bytes = ByteArray(imageSize + 2 * (imageSize / 4))
        if (isYuv420888PlanesNv21(yuv420888Planes, width, height)) {
            yuv420888Planes[0].buffer[nv21Bytes, 0, imageSize]// 复制 Y 的值
            yuv420888Planes[2].buffer[nv21Bytes, imageSize, 1]// 从 V 缓冲区获取第一个 V 值，因为 U 缓冲区不包含它
            yuv420888Planes[1].buffer[nv21Bytes, imageSize + 1, 2 * imageSize / 4 - 1]// 从 U 缓冲区复制第一个 U 值和剩余的 VU 值
        } else {
            // 回退到一个一个地复制 UV 值，这更慢但也有效
            unpackYuv420888Plane(yuv420888Planes[0], width, height, nv21Bytes, 0, 1)// 取 Y.
            unpackYuv420888Plane(yuv420888Planes[1], width, height, nv21Bytes, imageSize + 1, 2)// 取 U.
            unpackYuv420888Plane(yuv420888Planes[2], width, height, nv21Bytes, imageSize, 2)// 取 V.
        }
        return nv21Bytes
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 检查 YUV_420_888 图像的 UV平面缓冲区是否为 NV21 格式
     */
    @JvmStatic
    fun isYuv420888PlanesNv21(planes: Array<Plane>, width: Int, height: Int): Boolean {
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
     */
    @JvmStatic
    fun unpackYuv420888Plane(plane: Plane, width: Int, height: Int, out: ByteArray, offset: Int, pixelStride: Int) {
        val buffer = plane.buffer
        buffer.rewind()

        // 计算当前平面的大小。假设它的纵横比与原始图像相同
        val numRow = (buffer.limit() + plane.rowStride - 1) / plane.rowStride
        if (numRow == 0)
            return
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