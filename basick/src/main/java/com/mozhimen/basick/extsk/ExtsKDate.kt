package com.mozhimen.basick.extsk

import com.mozhimen.basick.utilk.UtilKDate
import java.util.*

/**
 * @ClassName ExtsKDate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/30 16:04
 * @Version 1.0
 */
/**
 * date转String
 * @receiver Date
 * @param formatDate String
 * @param locale Locale?
 * @return String
 */
fun Date.date2String(formatDate: String, locale: Locale = Locale.CHINA): String = UtilKDate.date2String(this, formatDate, locale)

/**
 * long转String
 * @receiver Long
 * @param formatDate String
 * @param locale Locale?
 * @return String
 */
fun Long.long2String(formatDate: String, locale: Locale = Locale.CHINA): String = UtilKDate.long2String(this, formatDate, locale)

/**
 * string转date
 * @receiver String
 * @param formatDate String
 * @param locale Locale?
 * @return Date
 */
fun String.string2Date(formatDate: String, locale: Locale = Locale.CHINA): Date = UtilKDate.string2Date(this, formatDate, locale)