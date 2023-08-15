package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap


/**
 * @ClassName UtilKBitmap
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 14:21
 * @Version 1.0
 */
object UtilKBitmap {
    @JvmStatic
    fun getSizeOfM(bitmap: Bitmap): Int =
        bitmap.byteCount / 1024 / 1024
}