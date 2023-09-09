package com.mozhimen.basick.utilk.android.provider

import android.net.Uri
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.database.getColumnString
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKMideaStore
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/2 23:20
 * @Version 1.0
 */
fun Uri.getMediaColumnsString(selection: String? = null, selectionArgs: Array<String>? = null): String? =
    UtilKMediaStore.getMediaColumnsString(this, selection, selectionArgs)

object UtilKMediaStore : BaseUtilK() {
    @JvmStatic
    fun getMediaColumnsString(uri: Uri, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        try {
            val cursor = UtilKContentResolver.query(_context, uri, arrayOf(CMediaStore.MediaColumns.DATA), selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val data = cursor.getColumnString(CMediaStore.MediaColumns.DATA)
                    if (data != "null")
                        return data
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }
}