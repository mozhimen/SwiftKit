package com.mozhimen.basick.utilk.android.text

import java.util.Date
import java.util.Locale

/**
 * @ClassName UtilKDateFormatFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/14
 * @Version 1.0
 */
fun Date.date2strDate(locale: Locale, skeleton: String): CharSequence =
    UtilKDateFormatFormat.date2strDate(this, locale, skeleton)

///////////////////////////////////////////////////////////////////////

object UtilKDateFormatFormat {
    @JvmStatic
    fun date2strDate(date: Date, locale: Locale, skeleton: String): CharSequence =
        UtilKDateFormat.format(UtilKDateFormat.getBestDateTimePattern(locale, skeleton), date)
}