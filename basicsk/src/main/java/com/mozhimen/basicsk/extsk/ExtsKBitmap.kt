package com.mozhimen.basicsk.extsk

import android.graphics.Bitmap
import com.mozhimen.basicsk.utilk.UtilKBitmap

/**
 * @ClassName ExtsKBitmap
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/1 13:48
 * @Version 1.0
 */
/**
 * url转Bitmap
 * @receiver String
 * @param placeholder Int
 * @param error Int
 * @param onGetBitmap Function1<Bitmap, Unit>
 */
fun String.url2Bitmap(
    placeholder: Int = android.R.color.black,
    error: Int = android.R.color.black,
    onGetBitmap: (Bitmap) -> Unit
) {
    UtilKBitmap.url2Bitmap(this, placeholder, error, onGetBitmap)
}