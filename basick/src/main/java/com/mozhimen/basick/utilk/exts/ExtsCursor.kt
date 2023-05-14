package com.mozhimen.basick.utilk.exts

import android.annotation.SuppressLint
import android.database.Cursor

@SuppressLint("Range")
fun Cursor.getStringValue(key: String) = getString(getColumnIndex(key))
