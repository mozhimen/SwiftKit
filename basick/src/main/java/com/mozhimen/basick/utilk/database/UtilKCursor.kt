package com.mozhimen.basick.utilk.database

import android.annotation.SuppressLint
import android.database.Cursor

/**
 * @ClassName UtilKCursor
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 10:31
 * @Version 1.0
 */
fun Cursor.getStringValue(key: String): String =
    UtilKCursor.getStringValue(this, key)

object UtilKCursor {
    @SuppressLint("Range")
    @JvmStatic
    fun getStringValue(cursor: Cursor, key: String): String =
        cursor.getString(cursor.getColumnIndex(key))
}