package com.mozhimen.basick.utilk.android.webkit

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver

/**
 * @ClassName UtilKMimeTypeMap
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/6 0:20
 * @Version 1.0
 */
object UtilKMimeTypeMap {
    @JvmStatic
    fun get():MimeTypeMap =
        MimeTypeMap.getSingleton()

    @JvmStatic
    fun getExtensionFromMimeType(context: Context, uri: Uri): String? =
        get().getExtensionFromMimeType(UtilKContentResolver.getType(context, uri))
}