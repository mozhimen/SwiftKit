package com.mozhimen.basick.utilk.androidx.exifinterface

import androidx.exifinterface.media.ExifInterface
import com.mozhimen.basick.elemk.android.media.cons.CExifInterface
import java.io.IOException

/**
 * @ClassName UtilKExifInterface
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/20
 * @Version 1.0
 */
object UtilKExifInterface {
    @JvmStatic
    fun getBitmapDegree(strBitmapPathName: String) :Int{
        var degree = 0
        try {
            val exifInterface = ExifInterface(strBitmapPathName)// 从指定路径下读取图片，并获取其EXIF信息
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