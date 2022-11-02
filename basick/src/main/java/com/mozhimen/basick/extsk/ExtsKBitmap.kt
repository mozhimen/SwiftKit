package com.mozhimen.basick.extsk

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapConv
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapNet
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapTrans

/**
 * @ClassName ExtsKBitmap
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/1 13:48
 * @Version 1.0
 */
/**
 * bytes转位图
 * @receiver ByteArray
 * @return Bitmap
 */
fun ByteArray.bytes2Bitmap(): Bitmap =
    UtilKBitmapTrans.bytes2Bitmap(this)

/**
 * 位图转bytes
 * @receiver Bitmap
 * @return ByteArray?
 */
fun Bitmap.bitmap2bytes(): ByteArray? =
    UtilKBitmapTrans.bitmap2Bytes(this)

/**
 * gradientDrawable转Bitmap
 * @receiver Drawable
 * @return Bitmap
 */
fun Drawable.drawable2Bitmap(width: Int = this.intrinsicWidth, height: Int = this.intrinsicHeight): Bitmap =
    UtilKBitmapTrans.drawable2Bitmap(this, width, height)

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
    UtilKBitmapConv.cropBitmap(this, width, height, x, y)