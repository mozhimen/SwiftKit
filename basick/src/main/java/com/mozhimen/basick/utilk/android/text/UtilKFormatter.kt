package com.mozhimen.basick.utilk.android.text

import android.text.format.Formatter
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/26 16:55
 * @Version 1.0
 */
fun Long.getFormatFileSize(): String =
    UtilKFormatter.getFormatFileSize(this)

object UtilKFormatter : BaseUtilK() {
    @JvmStatic
    fun getFormatFileSize(bytes: Long): String =
        Formatter.formatFileSize(_context, bytes)
}