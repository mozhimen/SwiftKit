package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mozhimen.basick.logk.LogK
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.*
import kotlin.coroutines.resume
import kotlin.math.ceil


/**
 * @ClassName UtilKImage
 * @Description <uses-permission android:name="android.permission.INTERNET" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */
object UtilKBitmap {
    private val TAG = "UtilKBitmap>>>>>"

    /**
     * bytes转位图
     * @param bytes ByteArray
     * @return Bitmap
     */
    fun bytes2Bitmap(bytes: ByteArray): Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

    /**
     * 位图转bytes
     * @param bitmap Bitmap
     * @return ByteArray?
     */
    fun bitmap2Bytes(bitmap: Bitmap): ByteArray? {
        val baos = ByteArrayOutputStream(bitmap.width * bitmap.height * 4)
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            //将字节数组输出流转化为字节数组byte[]
            return baos.toByteArray()
        } catch (e: Exception) {
            LogK.et(TAG, "bitmap2Bytes Exception ${e.message}")
        } finally {
            try {
                baos.flush()
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * 裁剪图片
     * @param origin Bitmap
     * @param width Int
     * @param height Int
     * @param x Int
     * @param y Int
     * @return Bitmap
     */
    fun cropBitmap(origin: Bitmap, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val originWidth: Int = origin.width // 得到图片的宽，高
        val originHeight: Int = origin.height
        val cropWidth = if (width >= originWidth) originWidth else width // 裁切后所取的正方形区域边长
        val cropHeight = if (height >= originHeight) originHeight else height
        return Bitmap.createBitmap(origin, x, y, cropWidth, cropHeight, null, false)
    }

    /**
     * 从相册获得图片
     * @param activity Activity
     * @param intent Intent?
     * @return Bitmap?
     */
    fun getFromAlbum(activity: Activity, intent: Intent?): Bitmap? {
        val uri = intent?.data ?: return null
        try {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(activity.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun uri2Bitmap(activity: Activity,uri: Uri): Bitmap? {
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
            //Bitmap btm = BitmapFactory.decodeStream(inputStream);
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
     * 获取Bitmap
     * @param url String
     * @param placeholder Int
     * @param error Int
     * @param onGetBitmap Function1<Bitmap, Unit>
     */
    fun url2Bitmap(
        url: String,
        placeholder: Int = android.R.color.black,
        error: Int = android.R.color.black,
        onGetBitmap: (Bitmap) -> Unit
    ) {
        Glide.with(UtilKGlobal.instance.getApp()!!).asBitmap().load(url)
            .transition(withCrossFade()).placeholder(placeholder).error(error).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    onGetBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    /**
     * 协程方式 获取Bitmap
     * @param url String
     * @param placeholder Int
     * @param error Int
     * @return Bitmap
     */
    suspend fun url2Bitmap2(
        url: String,
        placeholder: Int = android.R.color.black,
        error: Int = android.R.color.black
    ): Bitmap = suspendCancellableCoroutine { coroutine ->
        Glide.with(UtilKGlobal.instance.getApp()!!).asBitmap().load(url)
            .transition(withCrossFade()).placeholder(placeholder).error(error).into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        coroutine.resume(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
    }


    /**
     * nv21转文件
     * @param nv21 ByteArray
     * @param width Int
     * @param height Int
     * @param filePath String
     * @return File
     */
    fun nv21Array2File(nv21: ByteArray, width: Int, height: Int, filePath: String): File {
        return bitmap2File(nv21Array2Bitmap(nv21, width, height), filePath)
    }

    /**
     * nv21转位图
     * @param nv21 ByteArray
     * @param width Int
     * @param height Int
     * @return Bitmap
     */
    fun nv21Array2Bitmap(nv21: ByteArray, width: Int, height: Int): Bitmap {
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(
            Rect(0, 0, yuvImage.width, yuvImage.height), 100,
            out
        )
        val data = out.toByteArray()
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    /**
     * 位图转文件
     * @param bitmap Bitmap
     * @param filepath String
     * @return File
     */
    fun bitmap2File(bitmap: Bitmap, filepath: String): File {
        val file = File(filepath) //将要保存图片的路径
        //        if (bitmap != null) {
        UtilKFile.deleteFile(file)
        var bos: BufferedOutputStream? = null
        try {
            bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (bos != null) {
                try {
                    bos.flush()
                    bos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return file
    }

    /**
     * drawable转位图
     * @param drawable Drawable
     * @return Bitmap
     */
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
     * 旋转位图
     * @param sourceBitmap Bitmap
     * @param degree Int
     * @param flipX Boolean
     * @param flipY Boolean
     * @return Bitmap
     */
    fun rotateBitmap(sourceBitmap: Bitmap, degree: Int, flipX: Boolean = false, flipY: Boolean = false): Bitmap {
        val matrix = Matrix()
        matrix.postRotate((degree).toFloat())
        matrix.postScale(if (flipX) -1f else 1f, if (flipY) -1f else 1f)
        return Bitmap.createBitmap(
            sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, true
        )
    }

    /**
     * 匹配角度
     * @param sourceBitmap Bitmap
     * @param degree Int
     * @return Bitmap
     */
    fun adjustPhotoRotation(sourceBitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.setRotate(
            degree.toFloat(),
            sourceBitmap.width.toFloat() / 2,
            sourceBitmap.height.toFloat() / 2
        )
        val outputX: Float
        val outputY: Float
        if (degree == 90) {
            outputX = sourceBitmap.height.toFloat()
            outputY = 0f
        } else {
            outputX = sourceBitmap.height.toFloat()
            outputY = sourceBitmap.width.toFloat()
        }
        val values = FloatArray(9)
        matrix.getValues(values)
        val x1 = values[Matrix.MTRANS_X]
        val y1 = values[Matrix.MTRANS_Y]
        matrix.postTranslate(outputX - x1, outputY - y1)
        val outputBitmap =
            Bitmap.createBitmap(sourceBitmap.height, sourceBitmap.width, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        val canvas = Canvas(outputBitmap)
        canvas.drawBitmap(sourceBitmap, matrix, paint)
        return outputBitmap
    }

    /**
     * 滤镜图片
     * @param sourceBitmap Bitmap
     * @param tintColor Int 过滤颜色
     * @return Bitmap
     */
    fun tintBitmap(sourceBitmap: Bitmap, tintColor: Int): Bitmap {
        val outputBitmap = Bitmap.createBitmap(sourceBitmap.width, sourceBitmap.height, sourceBitmap.config)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(sourceBitmap, 0f, 0f, paint)
        return outputBitmap
    }

    /**
     * 保存图片
     * @param savePath String
     * @param bitmap Bitmap?
     */
    fun saveBitmap(savePath: String, bitmap: Bitmap?) {
        if (null == bitmap) // 容错处理
            return
        val f = File(savePath)
        val pareFile: File? = f.parentFile
        if (pareFile?.exists() == false) {
            pareFile.mkdirs()
        }
        val out: FileOutputStream
        try {
            f.createNewFile()
            out = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap,为了避免图片太大，这里对图片进行了压缩
     * @param picturePath String
     * @return Bitmap?
     */
    fun getDecodableBitmap(picturePath: String): Bitmap? {
        return try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(picturePath, options)
            var sampleSize = options.outHeight / 400
            if (sampleSize <= 0) {
                sampleSize = 1
            }
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
            BitmapFactory.decodeFile(picturePath, options)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}