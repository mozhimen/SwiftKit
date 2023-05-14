package com.mozhimen.basick.utilk.exts

import android.graphics.Color

fun Int.getContrastColor(): Int {
    val y = (299 * Color.red(this) + 587 * Color.green(this) + 114 * Color.blue(this)) / 1000
    return if (y >= 149 && this != Color.BLACK) 0xFF333333.toInt() else Color.WHITE
}

fun Int.toHex() = String.format("#%06X", 0xFFFFFF and this).toUpperCase()