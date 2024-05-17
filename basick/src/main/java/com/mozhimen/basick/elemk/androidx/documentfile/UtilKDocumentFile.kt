package com.mozhimen.basick.elemk.androidx.documentfile

import androidx.documentfile.provider.DocumentFile

/**
 * @ClassName UtilKDocumentFile
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
fun DocumentFile.isZipped(): Boolean =
    UtilKDocumentFile.isZipped(this)

//////////////////////////////////////////////////////////////////////////////

object UtilKDocumentFile {
    @JvmStatic
    fun isZipped(documentFile: DocumentFile): Boolean =
        documentFile.type == "application/zip"
}