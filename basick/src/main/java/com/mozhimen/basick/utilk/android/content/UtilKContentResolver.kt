package com.mozhimen.basick.utilk.android.content

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.io.InputStream
import java.io.OutputStream

/**
 * @ClassName UtilKContentResolver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 18:12
 * @Version 1.0
 */
object UtilKContentResolver : BaseUtilK() {
    @JvmStatic
    fun get(context: Context): ContentResolver =
        UtilKContext.getContentResolver(context)

    @JvmStatic
    fun getType(context: Context, uri: Uri): String? =
        getType(get(context), uri)

    @JvmStatic
    fun getType(contentResolver: ContentResolver, uri: Uri): String? =
        contentResolver.getType(uri)

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun query(context: Context, @RequiresPermission.Read uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? =
        get(context).query(uri, projection, selection, selectionArgs, sortOrder)

    @JvmStatic
    fun openInputStream(context: Context, uri: Uri): InputStream? =
        get(context).openInputStream(uri)

    @JvmStatic
    fun openOutputStream(context: Context, uri: Uri): OutputStream? =
        get(context).openOutputStream(uri)

    @JvmStatic
    fun insert(context: Context, @RequiresPermission.Write uri: Uri, values: ContentValues?): Uri? =
        get(context).insert(uri, values)

    @JvmStatic
    fun delete(context: Context, @RequiresPermission.Write uri: Uri, where: String?, selectionArgs: Array<String>?) {
        get(context).delete(uri, where, selectionArgs)
    }

    @JvmStatic
    fun deleteImageFile(filePathWithName: String) {
        delete(_context, CMediaStore.Images.Media.EXTERNAL_CONTENT_URI, "${CMediaStore.Images.Media.DATA}='${filePathWithName}'", null)
    }
}