package com.mozhimen.basick.utilk.android.util

import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import androidx.annotation.IntRange
import com.mozhimen.basick.elemk.android.util.cons.CBase64
import com.mozhimen.basick.utilk.android.graphics.anyBitmap2anyBytes
import com.mozhimen.basick.utilk.android.graphics.anyBytes2anyBitmap
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.bytes2byteArrayInputStream
import com.mozhimen.basick.utilk.java.io.inputStream2bitmapDrawable

/**
 * @ClassName UtilKBase64
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 19:56
 * @Version 1.0
 */
fun ByteArray.bytes2strBase64(flags: Int = CBase64.NO_WRAP): String =
    UtilKBase64.bytes2strBase64(this, flags)

fun String.strBase642bytes(flags: Int = CBase64.DEFAULT): ByteArray =
    UtilKBase64.strBase642bytes(this, flags)

fun ByteArray.bytesBase642bytes(flags: Int = CBase64.DEFAULT): ByteArray =
    UtilKBase64.bytesBase642bytes(this, flags)

object UtilKBase64 : BaseUtilK() {

    @JvmStatic
    fun bytes2strBase64(bytes: ByteArray, flags: Int = CBase64.NO_WRAP): String =
        Base64.encodeToString(bytes, flags)

    @JvmStatic
    fun strBase642bytes(strBase64: String, flags: Int = CBase64.DEFAULT): ByteArray =
        Base64.decode(strBase64, flags)

    @JvmStatic
    fun bytesBase642bytes(bytesBase64: ByteArray, flags: Int = CBase64.DEFAULT): ByteArray =
        Base64.decode(bytesBase64, flags)

    /**
     * base64转位图
     */
    @JvmStatic
    fun strBase642bitmap(strBase64: String, flags: Int = CBase64.DEFAULT): Bitmap =
        strBase64.strBase642bytes(flags).anyBytes2anyBitmap()

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
    fun bitmap2strBase64(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 50, flags: Int = CBase64.NO_WRAP): String? {
        return sourceBitmap.anyBitmap2anyBytes(compressFormat, quality)?.bytes2strBase64(flags)
    }

    @JvmStatic
    fun strBase642bitmapDrawable(strBase64: String, flags: Int = CBase64.DEFAULT): BitmapDrawable =
        strBase64.strBase642bytes(flags).bytes2byteArrayInputStream().inputStream2bitmapDrawable()
}