package com.mozhimen.basick.utilk.android.provider

import android.net.Uri
import android.provider.MediaStore
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.database.getString
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKMideaStore
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/2 23:20
 * @Version 1.0
 */
fun Uri.getDataColumn(selection: String? = null, selectionArgs: Array<String>? = null): String? =
    UtilKMediaStore.getDataColumn(this, selection, selectionArgs)

object UtilKMediaStore : BaseUtilK() {
    @JvmStatic
    fun getDataColumn(uri: Uri, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        try {
            val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
            val cursor = UtilKContentResolver.query(_context, uri, projection, selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val data = cursor.getString(MediaStore.Files.FileColumns.DATA)
                    if (data != "null") return data
                }
            }
        } catch (e: Exception) {
            e.message?.et(TAG)
            e.printStackTrace()
        }
        return null
    }
}