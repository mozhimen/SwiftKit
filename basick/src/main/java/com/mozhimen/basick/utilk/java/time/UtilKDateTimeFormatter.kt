package com.mozhimen.basick.utilk.java.time

import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * @ClassName UtilKDateTimeFormatter
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
object UtilKDateTimeFormatter {
    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    fun ofLocalizedDate(dateStyle: FormatStyle): DateTimeFormatter =
        DateTimeFormatter.ofLocalizedDate(dateStyle)
}