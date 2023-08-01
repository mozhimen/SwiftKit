package com.mozhimen.basick.utilk.android.util

import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapFormat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.jpegBytes2jpegBitmap
import java.io.ByteArrayInputStream
import java.lang.Exception

/**
 * @ClassName UtilKMd5
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 19:56
 * @Version 1.0
 */
fun String.base64Str2bytes(flags: Int = Base64.DEFAULT): ByteArray =
    UtilKBase64.base64Str2bytes(this, flags)

object UtilKBase64 : BaseUtilK() {
    @JvmStatic
    fun base64Str2bytes(base64Str: String, flags: Int = Base64.DEFAULT): ByteArray =
        Base64.decode(base64Str, flags)

    @JvmStatic
    fun base64Str2bitmapDrawable(base64Str: String): BitmapDrawable? {
        val byteArrayInputStream = ByteArrayInputStream(base64Str2bytes(base64Str))
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
    fun base64Str2bitmap(base64Str: String): Bitmap =
        base64Str.base64Str2bytes().jpegBytes2jpegBitmap()

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
    fun bitmap2base64Str(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 50): String? {
        val bitmapBytes: ByteArray = UtilKBitmapFormat.anyBitmap2anyBytes(sourceBitmap, compressFormat, quality) ?: return null
        return Base64.encodeToString(bitmapBytes, Base64.NO_WRAP)
    }
}