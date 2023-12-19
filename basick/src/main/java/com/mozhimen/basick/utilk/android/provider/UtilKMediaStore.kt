package com.mozhimen.basick.utilk.android.provider

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
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
fun Uri.getMediaColumns(selection: String? = null, selectionArgs: Array<String>? = null): String? =
    UtilKMediaStore.getMediaColumns(this, selection, selectionArgs)

////////////////////////////////////////////////////////////////////////////////////

object UtilKMediaStore : BaseUtilK() {

    /**
     * api 24?
     */
    @JvmStatic
    fun getMediaColumns(uri: Uri, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        try {
            val cursor = UtilKContentResolver.query(_context, uri, arrayOf(CMediaStore.MediaColumns.DATA), selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(CMediaStore.MediaColumns.DATA)
                    if (index == -1) return null
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

    @JvmStatic
    fun getImagesMediaBitmap(context: Context, uri: Uri): Bitmap =
        MediaStore.Images.Media.getBitmap(UtilKContentResolver.get(context), uri)
}