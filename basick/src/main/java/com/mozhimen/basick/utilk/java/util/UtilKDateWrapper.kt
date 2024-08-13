package com.mozhimen.basick.utilk.java.util

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.text.UtilKSimpleDateFormatFormat
import java.util.Date
import java.util.Locale

/**
 * @ClassName UtilKDate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 14:08
 * @Version 1.0
 */
object UtilKDateWrapper : BaseUtilK() {
    //获取现在日期
    @JvmStatic
    fun getNowDate(): Date =
        UtilKDate.get()

    //获取当前时间
    @JvmStatic
    fun getNowStr(strFormatDate: String = CDateFormat.Format.yyyy_MM_dd_HH_mm_ss, locale: Locale = Locale.CHINA): String =
        UtilKSimpleDateFormatFormat.date2strDate(getNowDate(), strFormatDate, locale)

    //获取现在日期
    @JvmStatic
    fun getNowLong(): Long =
        UtilKSimpleDateFormatFormat.date2longDate(getNowDate())

    /////////////////////////////////////////////////////////////////////////////////////

    //获取当前小时
    @JvmStatic
    fun getCurrentHourStr(locale: Locale = Locale.CHINA): String =
        UtilKSimpleDateFormatFormat.date2strDate(getNowDate(), CDateFormat.Format.yyyy_MM_dd_HH, locale)

    //获取当前小时2位
    @JvmStatic
    fun getCurrentHourStr_ofHH(): String =
        getNowStr(CDateFormat.Format.HH)

    @JvmStatic
    fun getCurrentHourStr_ofHHmm(): String =
        getNowStr(CDateFormat.Format.HH_mm)

    //获取当前小时
    @JvmStatic
    fun getCurrentHourLong(locale: Locale = Locale.CHINA): Long? =
        UtilKSimpleDateFormatFormat.strDate2longDate(getCurrentHourStr(locale), CDateFormat.Format.yyyy_MM_dd_HH, locale)

    //今日
    @JvmStatic
    @JvmOverloads
    fun getTodayStr(locale: Locale = Locale.CHINA, strFormatDate: String = CDateFormat.Format.yyyy_MM_dd): String =
        UtilKSimpleDateFormatFormat.date2strDate(getNowDate(), strFormatDate, locale)

    //今日Long
    @JvmStatic
    fun getTodayLong(locale: Locale = Locale.CHINA): Long? =
        UtilKSimpleDateFormatFormat.strDate2longDate(getTodayStr(locale), CDateFormat.Format.yyyy_MM_dd, locale)
}