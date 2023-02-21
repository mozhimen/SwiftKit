package com.mozhimen.basick.utilk.bitmap

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.basick.utilk.file.UtilKFile
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.IntBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.ceil


/**
 * @ClassName UtilKBitmapFormat
 * @Description Bitmap bytes 流转换类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */
object UtilKBitmapFormat {
    private val TAG = "UtilKBitmapFormat>>>>>"
    private val _context = UtilKApplication.instance.get()

    /**
     * image转Bytes
     * @param image Image
     * @return ByteArray
     */
    @JvmStatic
    @Throws(Exception::class)
    fun image2Bytes(image: Image): ByteArray {
        val bytes: ByteArray?
        when (image.format) {
            ImageFormat.JPEG -> {
                val buffer = image.planes[0].buffer
                bytes = ByteArray(buffer.capacity())
                buffer.get(bytes)
            }
            ImageFormat.YUV_420_888 -> {
                bytes = nv21Bytes2JpegBytes(yuv420888Image2Nv21Bytes(image), image.width, image.height)
            }
            else -> {
                throw Exception("cannot handle this format")
            }
        }
        return bytes
    }

    /**
     * yuv420转nv21Bytes
     * @param image Image
     * @return ByteArray
     */
    @JvmStatic
    fun yuv420888Image2Nv21Bytes(image: Image): ByteArray {
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
     * nv21Bytes转JpegBytes
     * @param nv21Bytes ByteArray
     * @param width Int
     * @param height Int
     * @return ByteArray
     */
    @JvmStatic
    @Throws(Exception::class)
    fun nv21Bytes2JpegBytes(nv21Bytes: ByteArray, width: Int, height: Int, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): ByteArray {
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

    /**
     * yuv流转Jpeg文件
     * @param nv21Bytes ByteArray
     * @param filePathWithName String
     * @param width Int
     * @param height Int
     */
    @JvmStatic
    fun yuvBytes2JpegFile(nv21Bytes: ByteArray, filePathWithName: String, width: Int, height: Int, isOverwrite: Boolean = true): String {
        val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, byteArrayOutputStream)
            return UtilKFile.byteArrayOutputStream2File(byteArrayOutputStream, filePathWithName, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
        return UtilKFile.MSG_WRONG
    }

    /**
     * bytes转位图
     * @param bytes ByteArray
     * @return Bitmap
     */
    @JvmStatic
    fun bytes2Bitmap(bytes: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

    /**
     * bytes转位图
     * @param bytes ByteArray
     * @param width Int
     * @param height Int
     * @return Bitmap
     */
    @JvmStatic
    fun grba8888Bytes2Rgba8888Bitmap(bytes: ByteArray, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bytes))
        return bitmap
    }

    /**
     * 位图转bytes
     * @param sourceBitmap Bitmap
     * @return ByteArray?
     */
    @JvmStatic
    fun bitmap2Bytes(sourceBitmap: Bitmap): ByteArray? {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream(sourceBitmap.width * sourceBitmap.height * 4)
            sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            return byteArrayOutputStream.toByteArray()            //将字节数组输出流转化为字节数组byte[]
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
        return null
    }

    /**
     * uri转bitmap
     * @param uri Uri
     * @return Bitmap?
     */
    @JvmStatic
    fun uri2Bitmap(uri: Uri): Bitmap? {
        var stream: InputStream? = null
        var inputStream: InputStream? = null
        try {
            //根据uri获取图片的流
            inputStream = _context.contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true            //options的in系列的设置了，injustDecodeBound只解析图片的大小，而不加载到内存中去
            //1.如果通过options.outHeight获取图片的宽高，就必须通过decodeStream解析同options赋值
            //否则options.outHeight获取不到宽高
            BitmapFactory.decodeStream(inputStream, null, options)
            //2.通过 btm.getHeight()获取图片的宽高就不需要1的解析，我这里采取第一张方式
            //Bitmap btm = BitmapFactory.decodeStream(inputStream)
            //获取图片的宽高
            val outHeight = options.outHeight.toDouble()
            val outWidth = options.outWidth.toDouble()
            //heightPixels就是要压缩后的图片高度，宽度也一样
            val a = ceil((outHeight / UtilKScreen.getCurrentScreenHeight().toDouble())).toInt()
            val b = ceil((outWidth / UtilKScreen.getCurrentScreenWidth().toDouble())).toInt()
            //比例计算,一般是图片比较大的情况下进行压缩
            val max = a.coerceAtLeast(b)
            if (max > 1) {
                options.inSampleSize = max
            }
            //解析到内存中去
            options.inJustDecodeBounds = false
            //根据uri重新获取流，inputStream在解析中发生改变了
            stream = _context.contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(stream, null, options)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream?.close()
            stream?.close()
        }
    }

    /**
     * nv21转文件
     * @param nv21Bytes ByteArray
     * @param width Int
     * @param height Int
     * @param filePathWithName String
     * @return File
     */
    @JvmStatic
    fun nv21Bytes2File(nv21Bytes: ByteArray, width: Int, height: Int, filePathWithName: String): String {
        return try {
            UtilKBitmapIO.bitmap2Album(nv21Bytes2JpegBitmap(nv21Bytes, width, height), filePathWithName)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKFile.MSG_WRONG
        }
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
    fun nv21Bytes2JpegBitmap(nv21Bytes: ByteArray, width: Int, height: Int): Bitmap {
        val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var byteArray: ByteArray?
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, byteArrayOutputStream)
            byteArray = byteArrayOutputStream.toByteArray()
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            throw e
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
    }

    /**
     * drawable转位图
     * @param drawable Drawable
     * @return Bitmap
     */
    @JvmStatic
    fun drawable2Bitmap(
        drawable: Drawable,
        width: Int = drawable.intrinsicWidth,
        height: Int = drawable.intrinsicHeight
    ): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val bitmap: Bitmap = if (width <= 0 || height <= 0) {
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
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
    fun clipNv21Bytes(
        nv21Bytes: ByteArray,
        width: Int,
        height: Int,
        cropX: Int,
        cropY: Int,
        cropWidth: Int,
        cropHeight: Int
    ): ByteArray {
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
     * gl10转bitmap
     * @param gl10 GL10
     * @param width Int
     * @param height Int
     * @param x Int
     * @param y Int
     * @return Bitmap
     */
    @JvmStatic
    @Throws(Exception::class)
    fun gl102Bitmap(gl10: GL10, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val bitmapBuffer = IntArray(width * height)
        val bitmapSource = IntArray(width * height)
        val intBuffer = IntBuffer.wrap(bitmapBuffer)
        intBuffer.position(0)
        gl10.glReadPixels(x, y, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer)
        var offset1: Int
        var offset2: Int
        for (i in 0 until height) {
            offset1 = i * width
            offset2 = (height - i - 1) * width
            for (j in 0 until width) {
                val texturePixel = bitmapBuffer[offset1 + j]
                val blue = texturePixel shr 16 and 0xff
                val red = texturePixel shl 16 and 0x00ff0000
                val pixel = texturePixel and -0xff0100 or red or blue
                bitmapSource[offset2 + j] = pixel
            }
        }
        return Bitmap.createBitmap(bitmapSource, width, height, Bitmap.Config.ARGB_8888)
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
    fun yuv422Bytes2Yuv420spBytes(y: ByteArray, u: ByteArray, v: ByteArray, nv21: ByteArray, stride: Int, height: Int) {
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
     * yuv420转yuv420sp
     * @param y ByteArray
     * @param u ByteArray
     * @param v ByteArray
     * @param nv21 ByteArray
     * @param stride Int
     * @param height Int
     */
    @JvmStatic
    fun yuv420Bytes2Yuv420spBytes(y: ByteArray, u: ByteArray, v: ByteArray, nv21: ByteArray, stride: Int, height: Int) {
        System.arraycopy(y, 0, nv21, 0, y.size)
        val length = y.size + u.size + v.size
        var uIndex = 0
        var vIndex = 0
        for (i in stride * height until length) {
            nv21[i] = v[vIndex++]
            nv21[i + 1] = u[uIndex++]
        }
    }
}