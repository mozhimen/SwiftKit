package com.mozhimen.basicsmk.utilmk

import android.graphics.*
import android.graphics.Bitmap
import java.io.*


/**
 * @ClassName UtilMKImage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */
object UtilMKImage {

    fun nv21Array2File(nv21: ByteArray?, width: Int, height: Int, filePath: String?): File? {
        return bitmap2File(nv21Array2Bitmap(nv21, width, height), filePath)
    }

    fun nv21Array2Bitmap(nv21: ByteArray?, width: Int, height: Int): Bitmap {
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(
            Rect(0, 0, yuvImage.width, yuvImage.height), 100,
            out
        )
        val data = out.toByteArray()
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    fun bitmap2File(bitmap: Bitmap, filepath: String?): File? {
        val file = File(filepath) //将要保存图片的路径
        //        if (bitmap != null) {
        UtilMKFile.deleteFile(file)
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
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {
                    bos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return file
    }

    fun rotateBitmap(sourceBitmap: Bitmap, degree: Int, isFrontCamera: Boolean = false): Bitmap {
        if (degree == 0) {
            return sourceBitmap
        }
        val rotateDegree = (degree).toFloat()
        val matrix = Matrix()
        matrix.postRotate(rotateDegree)
        if (isFrontCamera) {
            matrix.postScale(-1f, 1f)
        }
        return Bitmap.createBitmap(
            sourceBitmap, 0, 0, sourceBitmap.width, sourceBitmap.height, matrix, false
        )
    }

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
}