package com.mozhimen.basick.utilk.android.database

import android.database.Cursor

/**
 * @ClassName UtilKCursorFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/10
 * @Version 1.0
 */
fun Cursor.cursor2sequence(): Sequence<Cursor> =
    UtilKCursorFormat.cursor2sequence(this)

////////////////////////////////////////////////////////////////////////

object UtilKCursorFormat {
    @JvmStatic
    fun cursor2sequence(cursor: Cursor): Sequence<Cursor> =
        generateSequence { if (cursor.moveToNext()) cursor else null }
}