package com.mozhimen.basick.utilk.android.provider

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
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
            val projection = arrayOf(CMediaStore.Files.FileColumns.DATA)
            val cursor = UtilKContentResolver.query(_context, uri, projection, selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val data = cursor.getString(MediaStore.Files.FileColumns.DATA)
                    if (data != "null") return data
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    @JvmStatic
    fun getImagesMediaBitmap(context: Context, uri: Uri): Bitmap =
        MediaStore.Images.Media.getBitmap(UtilKContentResolver.get(_context), uri)
}