package com.mozhimen.basick.utilk.android.content

import android.content.ContentUris
import android.net.Uri

/**
 * @ClassName UtilKContentUris
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/5 20:39
 * @Version 1.0
 */
fun Uri.withAppendedId(id: Long): Uri =
    UtilKContentUris.withAppendedId(this, id)

object UtilKContentUris {
    @JvmStatic
    fun withAppendedId(contentUri: Uri, id: Long): Uri =
        ContentUris.withAppendedId(contentUri, id)
}