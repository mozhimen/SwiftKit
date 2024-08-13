package com.mozhimen.basick.utilk

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.java.text.date2strDate
import org.junit.Test
import java.util.Date
import java.util.Locale

/**
 * @ClassName TestUtilKDateFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
class TestUtilKDateFormatFormat {
    @Test
    fun date2strDate() {
        val date = Date()
        println(date.date2strDate(CDateFormat.DEFAULT, Locale.CHINESE))
        println(date.date2strDate(CDateFormat.FULL, Locale.CHINESE))
        println(date.date2strDate(CDateFormat.LONG, Locale.CHINESE))
        println(date.date2strDate(CDateFormat.MEDIUM, Locale.CHINESE))
        println(date.date2strDate(CDateFormat.SHORT, Locale.CHINESE))

        println("//////////////////////////////////////")
        println(date.date2strDate(CDateFormat.DEFAULT, Locale.ENGLISH))
        println(date.date2strDate(CDateFormat.FULL, Locale.ENGLISH))
        println(date.date2strDate(CDateFormat.LONG, Locale.ENGLISH))
        println(date.date2strDate(CDateFormat.MEDIUM, Locale.ENGLISH))
        println(date.date2strDate(CDateFormat.SHORT, Locale.ENGLISH))
        println("//////////////////////////////////////")

        println(date.date2strDate(CDateFormat.DEFAULT, Locale.KOREA))
        println(date.date2strDate(CDateFormat.FULL, Locale.KOREA))
        println(date.date2strDate(CDateFormat.LONG, Locale.KOREA))
        println(date.date2strDate(CDateFormat.MEDIUM, Locale.KOREA))
        println(date.date2strDate(CDateFormat.SHORT, Locale.KOREA))
    }
}