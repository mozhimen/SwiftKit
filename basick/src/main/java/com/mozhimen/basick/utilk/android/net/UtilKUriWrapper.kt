package com.mozhimen.basick.utilk.android.net

import android.content.ContentResolver
import android.net.Uri
import androidx.core.net.toFile
import com.mozhimen.basick.elemk.android.content.cons.CContentResolver
import com.mozhimen.basick.elemk.android.provider.cons.COpenableColumns
import com.mozhimen.basick.utilk.android.content.UtilKContentResolverWrapper

/**
 * @ClassName UtilKUriWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/21
 * @Version 1.0
 */
fun Uri.getDisplayName(contentResolver: ContentResolver): String =
    UtilKUriWrapper.getDisplayName(this, contentResolver)

//////////////////////////////////////////////////////////////////////////

object UtilKUriWrapper {
    @JvmStatic
    fun getDisplayName(uri: Uri, contentResolver: ContentResolver): String =
        if (uri.scheme == CContentResolver.SCHEME_FILE) uri.toFile().name
        else UtilKContentResolverWrapper.getString(uri, arrayOf(COpenableColumns.DISPLAY_NAME)) ?: ""
}