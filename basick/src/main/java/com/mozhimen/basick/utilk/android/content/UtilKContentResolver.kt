package com.mozhimen.basick.utilk.android.content

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.UriPermission
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.utilk.commons.IUtilK
import java.io.InputStream
import java.io.OutputStream

/**
 * @ClassName UtilKContentResolver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 18:12
 * @Version 1.0
 */
object UtilKContentResolver : IUtilK {
    @JvmStatic
    fun get(context: Context): ContentResolver =
        UtilKContext.getContentResolver(context)

    @JvmStatic
    fun getType(context: Context, uri: Uri): String? =
        getType(get(context), uri)

    @JvmStatic
    fun getType(contentResolver: ContentResolver, uri: Uri): String? =
        contentResolver.getType(uri)

    @JvmStatic
    fun getPersistedUriPermissions(context: Context): List<UriPermission> =
        get(context).persistedUriPermissions

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
    fun openFileDescriptor(context: Context, uri: Uri, mode: String): ParcelFileDescriptor? =
        get(context).openFileDescriptor(uri, mode)

    @JvmStatic
    fun insert(context: Context, @RequiresPermission.Write uri: Uri, values: ContentValues?): Uri? =
        get(context).insert(uri, values)

    @JvmStatic
    fun delete(context: Context, @RequiresPermission.Write uri: Uri, where: String?, selectionArgs: Array<String>?) {
        get(context).delete(uri, where, selectionArgs)
    }
}