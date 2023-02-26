package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.device.UtilKDate
import java.util.*

/**
 * @ClassName ExtsKDate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/30 16:04
 * @Version 1.0
 */
fun Date.date2Long(): Long =
    UtilKDate.date2Long(this)

fun Long.long2Date(): Date =
    UtilKDate.long2Date(this)

fun Date.date2String(formatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDate.date2String(this, formatDate, locale)

fun String.string2Date(formatDate: String, locale: Locale = Locale.CHINA): Date =
    UtilKDate.string2Date(this, formatDate, locale)

fun Long.long2String(formatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDate.long2String(this, formatDate, locale)

fun String.string2Long(formatDate: String, locale: Locale = Locale.CHINA): Long =
    UtilKDate.string2Long(this, formatDate, locale)