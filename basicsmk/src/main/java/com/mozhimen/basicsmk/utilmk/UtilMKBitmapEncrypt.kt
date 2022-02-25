package com.mozhimen.basicsmk.utilmk

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * @ClassName UtilMKBitmapEncrypt
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 19:38
 * @Version 1.0
 */
object UtilMKBitmapEncrypt {
    fun base64ToBitmap(base64String: String?): Bitmap? {
        val timeMillis = System.currentTimeMillis()
        val decode: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.size)
        Log.e("UtilMKBitmapEncrypt", "diff:" + (System.currentTimeMillis() - timeMillis))
        return bitmap
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    fun bitmapToBase64(bitmap: Bitmap?): String? {
        var result: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
                baos.flush()
                baos.close()
                val bitmapBytes: ByteArray = baos.toByteArray()
                /**
                 * flags参数说明
                 *
                 * URL_SAFE：安全的URL编码，base64转码过程中会生成“+”，“/”，“=”这些会被URL进行转码的特殊字符，导致前后台数据不同，所以需要将这些字符替代为URL不会进行转码的字符，保证数据同步；
                 * "-" -> "+"
                 * "_" -> "/"
                 * NO_WRAP：不换行
                 * NO_PADDING："="号补齐去除，base64会对字符进行串长度余4的"="的补位，需去除"="。
                 *
                 */
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.flush()
                    baos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }
}