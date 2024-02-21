package com.mozhimen.basick.utilk.android.content

import android.content.ClipData
import android.content.Context
import android.net.Uri

/**
 * @ClassName UtilKClipData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/21
 * @Version 1.0
 */
object UtilKClipData {
    @JvmStatic
    fun getNewPlainText(label: CharSequence, text: CharSequence): ClipData =
        ClipData.newPlainText(label, text)

    @JvmStatic
    fun getNewUri(context: Context, label: CharSequence, uri: Uri): ClipData =
        ClipData.newUri(context.contentResolver, label, uri)
}