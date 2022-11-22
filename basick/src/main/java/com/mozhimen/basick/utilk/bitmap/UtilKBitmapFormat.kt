package com.mozhimen.basick.utilk.bitmap

import android.app.Activity
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.util.Log
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.context.UtilKApplication
import java.io.*
import java.nio.ByteBuffer
import kotlin.math.ceil


/**
 * @ClassName UtilKBitmap
 * @Description
 * <uses-permission android:name="android.permission.INTERNET" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */
object UtilKBitmapFormat {
    private val TAG = "UtilKBitmap>>>>>"
    private val _context = UtilKApplication.instance.get()


    /**
     * image转Bytes
     * @param image Image
     * @return ByteArray
     */
    @JvmStatic
    fun image2Bytes(image: Image): ByteArray {
        var data: ByteArray? = null
        if (image.format == ImageFormat.JPEG) {
            val buffer = image.planes[0].buffer
            data = ByteArray(buffer.capacity())
            buffer.get(data)
        } else if (image.format == ImageFormat.YUV_420_888) {
            data = nv21Bytes2JpegBytes(yuv420Image2nv21Bytes(image), image.width, image.height)
        }
        return data!!
    }

    /**
     * yuv420转nv21Bytes
     * @param image Image
     * @return ByteArray
     */
    @JvmStatic
    fun yuv420Image2nv21Bytes(image: Image): ByteArray {
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
    fun nv21Bytes2JpegBytes(nv21Bytes: ByteArray, width: Int, height: Int): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val yuv = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        yuv.compressToJpeg(Rect(0, 0, width, height), 100, outputStream)
        return outputStream.toByteArray()
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
        val byteArrayOutputStream = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, byteArrayOutputStream)
        try {
            return UtilKFile.byteArrayOutputStream2File(byteArrayOutputStream, filePathWithName, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
        }
        return UtilKFile.msg_wrong
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
    fun bytes2Bitmap(bytes: ByteArray, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val bytesBuffer = ByteBuffer.wrap(bytes)
        bitmap.copyPixelsFromBuffer(bytesBuffer)
        return bitmap
    }

    /**
     * 位图转bytes
     * @param bitmap Bitmap
     * @return ByteArray?
     */
    @JvmStatic
    fun bitmap2Bytes(bitmap: Bitmap): ByteArray? {
        val baos = ByteArrayOutputStream(bitmap.width * bitmap.height * 4)
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            //将字节数组输出流转化为字节数组byte[]
            return baos.toByteArray()
        } catch (e: Exception) {
            Log.e(TAG, "bitmap2Bytes Exception ${e.message}")
            e.printStackTrace()
        } finally {
            try {
                baos.flush()
                baos.close()
            } catch (e: IOException) {
                Log.e(TAG, "bitmap2Bytes IOException ${e.message}")
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * uri转bitmap
     * @param activity Activity
     * @param uri Uri
     * @return Bitmap?
     */
    @JvmStatic
    fun uri2Bitmap(activity: Activity, uri: Uri): Bitmap? {
        var stream: InputStream? = null
        var inputStream: InputStream? = null
        try {
            //根据uri获取图片的流
            inputStream = activity.contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options()
            //options的in系列的设置了，injustdecodebouond只解析图片的大小，而不加载到内存中去
            options.inJustDecodeBounds = true
            //1.如果通过options.outHeight获取图片的宽高，就必须通过decodestream解析同options赋值
            //否则options.outheight获取不到宽高
            BitmapFactory.decodeStream(inputStream, null, options)
            //2.通过 btm.getHeight()获取图片的宽高就不需要1的解析，我这里采取第一张方式
            //Bitmap btm = BitmapFactory.decodeStream(inputStream)
            //以屏幕的宽高进行压缩
            val displayMetrics = activity.resources.displayMetrics
            val heightPixels = displayMetrics.heightPixels
            val widthPixels = displayMetrics.widthPixels
            //获取图片的宽高
            val outHeight = options.outHeight
            val outWidth = options.outWidth
            //heightPixels就是要压缩后的图片高度，宽度也一样
            val a = ceil((outHeight / heightPixels.toFloat()).toDouble()).toInt()
            val b = ceil((outWidth / widthPixels.toFloat()).toDouble()).toInt()
            //比例计算,一般是图片比较大的情况下进行压缩
            val max = a.coerceAtLeast(b)
            if (max > 1) {
                options.inSampleSize = max
            }
            //解析到内存中去
            options.inJustDecodeBounds = false
            //根据uri重新获取流，inputStream在解析中发生改变了
            stream = activity.contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(stream, null, options)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
                stream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * nv21转文件
     * @param nv21Bytes ByteArray
     * @param width Int
     * @param height Int
     * @param filePath String
     * @return File
     */
    /*@JvmStatic
    fun nv21Bytes2File(nv21Bytes: ByteArray, width: Int, height: Int, filePath: String): String {
        return bitmap2Album(nv21Bytes2Bitmap(nv21Bytes, width, height), filePath)
    }*/

    /**
     * nv21转位图 for camera1
     * @param nv21Bytes ByteArray
     * @param width Int
     * @param height Int
     * @return Bitmap
     */
    @JvmStatic
    fun nv21Bytes2Bitmap(nv21Bytes: ByteArray, width: Int, height: Int): Bitmap {
        val yuvImage = YuvImage(nv21Bytes, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(
            Rect(0, 0, yuvImage.width, yuvImage.height), 100,
            out
        )
        val data = out.toByteArray()
        return BitmapFactory.decodeByteArray(data, 0, data.size)
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
    ): Bitmap = if (drawable is BitmapDrawable) {
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

    /**
     * gl10转bitmap
     * @param gl10 GL10
     * @param width Int
     * @param height Int
     * @param x Int
     * @param y Int
     * @return Bitmap
     */
    /*@JvmStatic
    fun gl102Bitmap(gl10: GL10, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val bitmapBuffer = IntArray(width * height)
        val bitmapSource = IntArray(width * height)
        val intBuffer = IntBuffer.wrap(bitmapBuffer)
        intBuffer.position(0)
        try {
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
        } catch (e: GLException) {
            e.printStackTrace()
        }
        return Bitmap.createBitmap(bitmapSource, width, height, Bitmap.Config.ARGB_8888)
    }*/
}