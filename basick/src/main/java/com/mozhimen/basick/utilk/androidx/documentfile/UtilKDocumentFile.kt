package com.mozhimen.basick.utilk.androidx.documentfile

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.mozhimen.basick.utilk.kotlin.strUri2uri

/**
 * @ClassName UtilKDocumentFile
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/10 0:21
 * @Version 1.0
 */
object UtilKDocumentFile {

    @JvmStatic
    fun get(context: Context, strUriDocument: String): DocumentFile? =
        DocumentFile.fromTreeUri(context, strUriDocument.strUri2uri())

    @JvmStatic
    fun get(context: Context, uri: Uri): DocumentFile? =
        DocumentFile.fromTreeUri(context, uri)
}