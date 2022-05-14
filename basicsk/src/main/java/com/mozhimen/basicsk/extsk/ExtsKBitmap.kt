package com.mozhimen.basicsk.extsk

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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

/**
 * gradientDrawable转Bitmap
 * @receiver Drawable
 * @return Bitmap
 */
fun Drawable.drawable2Bitmap(width: Int = this.intrinsicWidth, height: Int = this.intrinsicHeight): Bitmap =
    UtilKBitmap.drawable2Bitmap(this, width, height)

/**
 * 裁剪图片
 * @receiver Bitmap
 * @param width Int
 * @param height Int
 * @param x Int
 * @param y Int
 * @return Bitmap
 */
fun Bitmap.cropBitmap(width: Int, height: Int, x: Int, y: Int): Bitmap =
    UtilKBitmap.cropBitmap(this, width, height, x, y)