package com.mozhimen.basick.utilk.exts

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.Drawable
import com.mozhimen.basick.utilk.graphics.bitmap.UtilKBitmapCompress
import com.mozhimen.basick.utilk.graphics.bitmap.UtilKBitmapDeal
import com.mozhimen.basick.utilk.graphics.bitmap.UtilKBitmapFormat

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
    UtilKBitmapFormat.bytes2Bitmap(this)

/**
 * 位图转bytes
 * @receiver Bitmap
 * @return ByteArray?
 */
fun Bitmap.bitmap2JpegBytes(): ByteArray? =
    UtilKBitmapFormat.bitmap2JpegBytes(this)

/**
 * gradientDrawable转Bitmap
 * @receiver Drawable
 * @return Bitmap
 */
fun Drawable.drawable2Bitmap(width: Int = this.intrinsicWidth, height: Int = this.intrinsicHeight): Bitmap =
    UtilKBitmapFormat.drawable2Bitmap(this, width, height)

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
    UtilKBitmapDeal.cropBitmap(this, width, height, x, y)

fun String.getCompressFormat(): CompressFormat =
    UtilKBitmapCompress.getCompressFormat(this)