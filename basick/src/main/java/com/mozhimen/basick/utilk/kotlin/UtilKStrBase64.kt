package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import com.mozhimen.basick.elemk.android.util.cons.CBase64
import com.mozhimen.basick.utilk.java.io.inputStream2bitmapDrawable

/**
 * @ClassName UtilKStrBase64
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:26
 * @Version 1.0
 */
fun String.strBase642bytes(flags: Int = CBase64.DEFAULT): ByteArray =
    UtilKStrBase64.strBase642bytes(this, flags)

fun String.strBase642bitmap(flags: Int = CBase64.DEFAULT): Bitmap =
    UtilKStrBase64.strBase642bitmap(this, flags)

fun String.strBase642bitmapDrawable(flags: Int = CBase64.DEFAULT): BitmapDrawable =
    UtilKStrBase64.strBase642bitmapDrawable(this, flags)

object UtilKStrBase64 {
    @JvmStatic
    fun strBase642bytes(strBase64: String, flags: Int = CBase64.DEFAULT): ByteArray =
        Base64.decode(strBase64, flags)

    /**
     * base64转位图
     */
    @JvmStatic
    fun strBase642bitmap(strBase64: String, flags: Int = CBase64.DEFAULT): Bitmap =
        strBase64.strBase642bytes(flags).bytes2bitmapAny()

    @JvmStatic
    fun strBase642bitmapDrawable(strBase64: String, flags: Int = CBase64.DEFAULT): BitmapDrawable =
        strBase64.strBase642bytes(flags).bytes2byteArrayInputStream().inputStream2bitmapDrawable()
}