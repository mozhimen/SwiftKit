package com.mozhimen.basick.utilk.android.provider

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract

/**
 * @ClassName UtilKDocumentsContract
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/6 0:12
 * @Version 1.0
 */
object UtilKDocumentsContract {
    @JvmStatic
    fun getDocumentId(documentUri: Uri): String =
        DocumentsContract.getDocumentId(documentUri)

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isDocumentUri(context: Context, uri: Uri): Boolean =
        DocumentsContract.isDocumentUri(context, uri)
}