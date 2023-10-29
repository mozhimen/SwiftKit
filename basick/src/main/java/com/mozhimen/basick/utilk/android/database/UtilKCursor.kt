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
inline fun <reified T> Cursor.getColumnValue(columnName: String): T? =
    UtilKCursor.getColumnValue(this, columnName)

fun Cursor.getColumnString(key: String): String =
    UtilKCursor.getColumnString(this, key)

object UtilKCursor {
    @JvmStatic
    fun getColumnIndex(cursor: Cursor, columnName: String): Int =
        cursor.getColumnIndex(columnName)

    @JvmStatic
    @SuppressLint("Range")
    fun getColumnString(cursor: Cursor, key: String): String =
        cursor.getString(getColumnIndex(cursor, key))

    @JvmStatic
    fun getColumnValueByType(cursor: Cursor, index: Int, clazz: Class<*>): Any? =
        when (clazz) {
            java.lang.String::class.java -> cursor.getString(index)
            java.lang.Long::class.java -> cursor.getLong(index)
            java.lang.Integer::class.java -> cursor.getInt(index)
            java.lang.Short::class.java -> cursor.getShort(index)
            java.lang.Float::class.java -> cursor.getFloat(index)
            java.lang.Double::class.java -> cursor.getDouble(index)
            java.lang.Boolean::class.java -> cursor.getInt(index) != 0
            ByteArray::class.java -> cursor.getBlob(index)
            else -> null
        }

    @JvmStatic
    inline fun <reified T> getColumnValue(cursor: Cursor, columnName: String): T? {
        val index = getColumnIndex(cursor, columnName)
        if (index == -1) return null
        return getColumnValueByType(cursor, index, T::class.java) as T?
    }
}