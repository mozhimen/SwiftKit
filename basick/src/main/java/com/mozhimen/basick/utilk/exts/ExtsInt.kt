package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.graphics.UtilKColor

fun Int.getContrastColor(): Int =
    UtilKColor.getContrastColor(this)

fun Int.colorInt2HexStr() =
    UtilKColor.colorInt2HexStr(this)