package com.mozhimen.basick.utilk.exts

import android.database.Cursor
import com.mozhimen.basick.utilk.database.UtilKCursor

fun Cursor.getStringValue(key: String): String =
    UtilKCursor.getStringValue(this, key)
