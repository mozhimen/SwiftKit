package com.mozhimen.basick.utilk.java.io.encode

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import com.mozhimen.basick.utilk.exts.et
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * @ClassName UtilKMd5
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 19:56
 * @Version 1.0
 */
object UtilKBase64 {
    private const val TAG = "UtilKBase64>>>>>"

    @JvmStatic
    fun str2BitmapDrawable(base64drawableStr: String): BitmapDrawable {
        return BitmapDrawable(null, ByteArrayInputStream(Base64.decode(base64drawableStr, Base64.DEFAULT)))
    }

    /**
     * base64转位图
     * @param string String
     * @return Bitmap?
     */
    @JvmStatic
    fun str2Bitmap(string: String): Bitmap {
        val decode: ByteArray = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decode, 0, decode.size)
    }

    /**
     * 位图转base64
     * flags参数说明
     * URL_SAFE：安全的URL编码，base64转码过程中会生成“+”，“/”，“=”这些会被URL进行转码的特殊字符，导致前后台数据不同，所以需要将这些字符替代为URL不会进行转码的字符，保证数据同步；
     * "-" -> "+"
     * "_" -> "/"
     * NO_WRAP：不换行
     * NO_PADDING："="号补齐去除，base64会对字符进行串长度余4的"="的补位，需去除"="。
     * @param sourceBitmap Bitmap
     * @return String?
     */
    @JvmStatic
    fun bitmap2Str(sourceBitmap: Bitmap, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 50): String? {
        var result: String? = null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            sourceBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            val bitmapBytes: ByteArray = byteArrayOutputStream.toByteArray()
            result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP)
        } catch (e: IOException) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
        return result
    }
}