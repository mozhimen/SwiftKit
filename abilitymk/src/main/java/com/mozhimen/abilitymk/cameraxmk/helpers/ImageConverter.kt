package com.mozhimen.abilitymk.cameraxmk.helpers

import android.graphics.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageProxy
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
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun yuv2Bitmap(image: ImageProxy): Bitmap {
        val planes: Array<ImageProxy.PlaneProxy> = image.planes
        //cameraX 获取yuv
        val yBuffer: ByteBuffer = planes[0].buffer
        val uBuffer: ByteBuffer = planes[1].buffer
        val vBuffer: ByteBuffer = planes[2].buffer

        val ySize: Int = yBuffer.remaining()
        val uSize: Int = uBuffer.remaining()
        val vSize: Int = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        //获取yuvImage
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        //输出流
        val out = ByteArrayOutputStream()
        //压缩写入out
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        //转数组
        val imageBytes: ByteArray = out.toByteArray()
        //生成bitmap
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun jpeg2Bitmap(image: ImageProxy): Bitmap {
        val planes: Array<ImageProxy.PlaneProxy> = image.planes
        val buffer = planes[0].buffer
        val size = buffer.remaining()
        val jpeg = ByteArray(size)
        buffer.get(jpeg, 0, size)
        return BitmapFactory.decodeByteArray(jpeg, 0, jpeg.size)
    }
}