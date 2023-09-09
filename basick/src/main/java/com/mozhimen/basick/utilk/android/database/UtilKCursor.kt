package com.mozhimen.basick.utilk.android.database

import android.annotation.SuppressLint
import android.database.Cursor

/**
 * @ClassName UtilKCursor
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 10:31
 * @Version 1.0
 */
fun Cursor.getColumnString(key: String): String =
    UtilKCursor.getColumnString(this, key)

object UtilKCursor {
    @JvmStatic
    fun getColumnIndex(cursor: Cursor, columnName: String): Int =
        cursor.getColumnIndex(columnName)

    @SuppressLint("Range")
    @JvmStatic
    fun getColumnString(cursor: Cursor, key: String): String =
        cursor.getString(getColumnIndex(cursor, key))
}