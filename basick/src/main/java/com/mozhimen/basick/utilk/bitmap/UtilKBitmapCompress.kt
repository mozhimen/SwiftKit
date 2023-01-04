package com.mozhimen.basick.utilk.bitmap

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import java.io.ByteArrayOutputStream


/**
 * @ClassName UtilKBitmapCompress
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/4 14:35
 * @Version 1.0
 */
object UtilKBitmapCompress {
    private const val TAG = "UtilKBitmapCompress>>>>>"
    fun compressQuality(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int): Bitmap {
        val byteArrayOutputStream = ByteArrayOutputStream()
        sourceBitmap.compress(CompressFormat.JPEG, quality, byteArrayOutputStream)
        val bytes: ByteArray = byteArrayOutputStream.toByteArray()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also {
            printBitmapInfo(it, bytes, quality)
        }
    }

//    fun compressSampleSize(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int): Bitmap {
//        val options = BitmapFactory.Options()
//        options.inSampleSize = 2
//
//        bm = BitmapFactory.decodeFile(
//            Environment
//                .getExternalStorageDirectory().absolutePath
//                    + "/DCIM/Camera/test.jpg", options
//        )
//        Log.i(
//            "wechat", ("压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
//                .toString() + "M宽度为" + bm.getWidth().toString() + "高度为" + bm.getHeight())
//        )
//    }

    private fun printBitmapInfo(bitmap: Bitmap, bytes: ByteArray, quality: Int) {
        Log.i(
            TAG,
            "compress after bitmap size: ${bitmap.byteCount / 1024 / 1024}M width: ${bitmap.width} height: ${bitmap.height} bytes.length: ${bytes.size / 1024}KB quality: $quality"
        )
    }
}