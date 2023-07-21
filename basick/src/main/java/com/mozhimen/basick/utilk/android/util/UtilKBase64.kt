package com.mozhimen.basick.utilk.android.util

import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapFormat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception

/**
 * @ClassName UtilKMd5
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 19:56
 * @Version 1.0
 */
object UtilKBase64 : BaseUtilK() {
    @JvmStatic
    fun str2ByteArray(base64Str: String, flags: Int = Base64.DEFAULT): ByteArray =
        Base64.decode(base64Str, flags)

    @JvmStatic
    fun str2BitmapDrawable(base64Str: String): BitmapDrawable? {
        val byteArrayInputStream: ByteArrayInputStream = ByteArrayInputStream(str2ByteArray(base64Str))
        return try {
            BitmapDrawable(null, byteArrayInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            byteArrayInputStream.close()
        }
    }

    /**
     * base64转位图
     * @param str String
     * @return Bitmap?
     */
    @JvmStatic
    fun str2Bitmap(base64Str: String): Bitmap {
        val decode: ByteArray = str2ByteArray(base64Str)
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
    fun bitmap2Str(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 50): String? {
        val bitmapBytes: ByteArray = UtilKBitmapFormat.bitmap2Bytes(sourceBitmap, compressFormat, quality) ?: return null
        return Base64.encodeToString(bitmapBytes, Base64.NO_WRAP)
    }
}