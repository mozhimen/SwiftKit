package com.mozhimen.basick.utilk.encrypt

import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import java.io.ByteArrayInputStream

/**
 * @ClassName UtilKMd5
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 19:56
 * @Version 1.0
 */
object UtilKBase64 {
    @JvmStatic
    fun str2BitmapDrawable(base64drawableStr: String): BitmapDrawable {
        return BitmapDrawable(null, ByteArrayInputStream(Base64.decode(base64drawableStr, Base64.DEFAULT)))
    }
}