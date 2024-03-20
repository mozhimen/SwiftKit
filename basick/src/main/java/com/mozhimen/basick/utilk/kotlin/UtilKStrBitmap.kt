package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.androidx.exifinterface.UtilKExifInterface

/**
 * @ClassName UtilKStrBitmap
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/20
 * @Version 1.0
 */
object UtilKStrBitmap {
    @JvmStatic
    fun getDegree(strBitmapPathName: String): Int =
        UtilKExifInterface.getBitmapDegree(strBitmapPathName)
}