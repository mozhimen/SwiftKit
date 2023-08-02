package com.mozhimen.basick.utilk.android.content

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.annotation.RequiresPermission
import java.io.InputStream

/**
 * @ClassName UtilKContentResolver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 18:12
 * @Version 1.0
 */
object UtilKContentResolver {
    @JvmStatic
    fun get(context: Context): ContentResolver =
        UtilKContext.getContentResolver(context)

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun query(context: Context, @RequiresPermission.Read uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? =
        get(context).query(uri, projection, selection, selectionArgs, sortOrder)

    @JvmStatic
    fun openInputStream(context: Context, uri: Uri): InputStream? =
        get(context).openInputStream(uri)

    @JvmStatic
    fun delete(context: Context, @RequiresPermission.Write uri: Uri, where: String?, selectionArgs: Array<String>?) {
        get(context).delete(uri, where, selectionArgs)
    }
}