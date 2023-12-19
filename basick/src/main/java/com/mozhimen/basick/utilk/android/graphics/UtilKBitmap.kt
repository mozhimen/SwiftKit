package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.media.ExifInterface
import com.mozhimen.basick.elemk.android.media.cons.CExifInterface
import java.io.IOException


/**
 * @ClassName UtilKBitmap
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 14:21
 * @Version 1.0
 */
fun Bitmap.getSizeOfM(): Int =
    UtilKBitmap.getSizeOfM(this)

object UtilKBitmap {
    @JvmStatic
    fun getSizeOfM(bitmap: Bitmap): Int =
        bitmap.byteCount / 1024 / 1024

    /**
     * 获取图片的旋转角度
     * @param bitmapPathName 图片绝对路径
     * @return 图片的旋转角度
     */
    @JvmStatic
    fun getDegree(bitmapPathName: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(bitmapPathName)// 从指定路径下读取图片，并获取其EXIF信息
            when (exifInterface.getAttributeInt(CExifInterface.TAG_ORIENTATION, CExifInterface.ORIENTATION_NORMAL)) {// 获取图片的旋转信息
                CExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                CExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                CExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree
    }
}