package com.mozhimen.basick.utilk.bitmap

import android.graphics.*
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.mozhimen.basick.utilk.context.UtilKApplication
import kotlin.math.roundToInt

/**
 * @ClassName UtilKBitmapBlur
 * @Description Bitmap 变换类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 22:49
 * @Version 1.0
 */
object UtilKBitmapDeal {
    private val _context = UtilKApplication.instance.get()

    /**
     * 旋转位图
     * @param sourceBitmap Bitmap
     * @param degree Int
     * @param flipX Boolean
     * @param flipY Boolean
     * @return Bitmap
     */
    @JvmStatic
    fun rotateBitmap(sourceBitmap: Bitmap, degree: Int, flipX: Boolean = false, flipY: Boolean = false): Bitmap {
        val matrix = Matrix()
        matrix.postRotate((degree).toFloat())
        matrix.postScale(if (flipX) -1f else 1f, if (flipY) -1f else 1f)
        return Bitmap.createBitmap(
            sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, true
        )
    }

    /**
     * 模糊图片,API>=17
     * @param bitmap Bitmap
     * @param bitmapScale Float 图片缩放比例
     * @param blurRadius Float 最大模糊度(0.0-25.0之间)
     * @return Bitmap
     */
    @JvmStatic
    fun blurBitmap(bitmap: Bitmap, bitmapScale: Float = 0.4f, blurRadius: Float = 25f): Bitmap {
        //将缩小后的图片作为预渲染的图片
        val inputBitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * bitmapScale).roundToInt(), (bitmap.height * bitmapScale).roundToInt(), false)
        //创建一张渲染后的输出图片
        val outputBitmap = Bitmap.createBitmap(inputBitmap)
        //创建RenderScript内核对象
        val renderScript = RenderScript.create(_context)
        //创建一个模糊效果的RenderScript的工具对象
        val scriptIntrinsicBlur =
            ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        //由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        //创建Allocation对象的时候其实内存时空的,需要使用copyTo()将数据填充进去
        val allocationIn = Allocation.createFromBitmap(renderScript, inputBitmap)
        val allocationOut = Allocation.createFromBitmap(renderScript, outputBitmap)
        //设置渲染的模糊程度,25f是最大模糊度
        scriptIntrinsicBlur.setRadius(blurRadius)
        //设置blurScript对象的输入内存
        scriptIntrinsicBlur.setInput(allocationIn)
        //将输出数据保存到输出内存中
        scriptIntrinsicBlur.forEach(allocationOut)
        //将数据填充到Allocation中
        allocationOut.copyTo(outputBitmap)

        return outputBitmap
    }

    /**
     * 将两个图片裁剪成一致
     * @param bitmap1 Bitmap
     * @param bitmap2 Bitmap
     * @param ratio Float
     * @return Pair<Bitmap, Bitmap>
     */
    @JvmStatic
    fun scaleSameSize(bitmap1: Bitmap, bitmap2: Bitmap, ratio: Float = 1f): Pair<Bitmap, Bitmap> {
        val minWidth = kotlin.math.min(bitmap1.width, bitmap2.width) * ratio
        val minHeight = kotlin.math.min(bitmap1.height, bitmap2.height) * ratio
        return scaleBitmap(bitmap1, minWidth.toInt(), minHeight.toInt()) to scaleBitmap(bitmap2, minWidth.toInt(), minHeight.toInt())
    }

    /**
     * 缩放原图
     * @param orgBmp Bitmap
     * @param destWidth Float
     * @param destHeight Float
     * @return Bitmap
     */
    @JvmStatic
    fun scaleBitmap(orgBmp: Bitmap, destWidth: Int, destHeight: Int): Bitmap {
        val ratioX: Float = destWidth.toFloat() / orgBmp.width.toFloat()
        val ratioY: Float = destHeight.toFloat() / orgBmp.height.toFloat()
        val matrix = Matrix()
        matrix.postScale(ratioX, ratioY)
        return Bitmap.createBitmap(orgBmp, 0, 0, orgBmp.width, orgBmp.height, matrix, true)
    }

    /**
     * 同比例放大图片
     * @param orgBmp Bitmap
     * @param ratio Int
     * @return Bitmap
     */
    @JvmStatic
    fun zoomBitmap(orgBmp: Bitmap, ratio: Float): Bitmap {
        if (ratio <= 1) return orgBmp
        val zoomWidth: Float = orgBmp.width / ratio
        val zoomHeight: Float = orgBmp.height / ratio
        val x: Float = (ratio - 1) * orgBmp.width / 2 / ratio
        val y: Float = (ratio - 1) * orgBmp.height / 2 / ratio
        return cropBitmap(orgBmp, zoomWidth.toInt(), zoomHeight.toInt(), x.toInt(), y.toInt())
    }

    /**
     * 裁剪图片
     * @param orgBmp Bitmap
     * @param width Int
     * @param height Int
     * @param x Int
     * @param y Int
     * @return Bitmap
     */
    @JvmStatic
    fun cropBitmap(orgBmp: Bitmap, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val originWidth: Int = orgBmp.width // 得到图片的宽，高
        val originHeight: Int = orgBmp.height
        val cropWidth = if (width >= originWidth) originWidth else width // 裁切后所取的正方形区域边长
        val cropHeight = if (height >= originHeight) originHeight else height
        return Bitmap.createBitmap(orgBmp, x, y, cropWidth, cropHeight, null, false)
    }

    /**
     * 匹配角度
     * @param sourceBitmap Bitmap
     * @param degree Int
     * @return Bitmap
     */
    @JvmStatic
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
    @JvmStatic
    fun tintBitmap(sourceBitmap: Bitmap, tintColor: Int): Bitmap {
        val outputBitmap = Bitmap.createBitmap(sourceBitmap.width, sourceBitmap.height, sourceBitmap.config)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(sourceBitmap, 0f, 0f, paint)
        return outputBitmap
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap,为了避免图片太大，这里对图片进行了压缩
     * @param picturePath String
     * @return Bitmap?
     */
    /*@JvmStatic
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
    }*/

    /**
     * 裁剪nv21Bytes
     * @param srcBytes ByteArray 原始数据
     * @param srcWidth Int 原始图像的width
     * @param srcHeight Int 原始图像height
     * @param cropX Int 裁剪区域左上角的x
     * @param cropY Int 裁剪区域左上角的y
     * @param cropWidth Int 裁剪的宽度
     * @param cropHeight Int 裁剪的高度
     * @return ByteArray 裁剪后的图像数据
     */
    /*@JvmStatic
    fun clipNv212Bytes(
        srcBytes: ByteArray,
        srcWidth: Int,
        srcHeight: Int,
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
            System.arraycopy(srcBytes, left + i * srcWidth, data, (i - top) * cropWidth, cropWidth)
        }
        // 按照YUV420SP格式，复制UV
        val startH = srcHeight + top / 2
        val endH = srcHeight + bottom / 2
        for (i in startH until endH) {
            System.arraycopy(srcBytes, left + i * srcWidth, data, (i - startH + cropHeight) * cropWidth, cropWidth)
        }
        val end = System.nanoTime()
        Log.d(TAG, "clipNv212Bytes clip use: " + (end - begin) + "ns")
        return data
    }*/

    /**
     * 堆叠Bitmap,最左边的在下面
     * @param bgBitmap Bitmap
     * @param fgBitmap Bitmap
     * @return Bitmap
     */
    /*@JvmStatic
    fun pileUpBitmap(bgBitmap: Bitmap, fgBitmap: Bitmap): Bitmap {
        val fgWidth: Int = fgBitmap.width
        val fgHeight: Int = fgBitmap.height

        val destBitmap = Bitmap.createBitmap(bgBitmap.width, bgBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(destBitmap)

        canvas.drawBitmap(bgBitmap, 0f, 0f, null)
        canvas.drawBitmap(fgBitmap, ((bgWidth - fgWidth) / 2).toFloat(), ((bgHeight - fgHeight) / 2).toFloat(), null)
        canvas.save()
        canvas.restore()
        return destBitmap
    }*/
}