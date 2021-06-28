package com.mozhimen.swiftmk.utils.date

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName DateUtil
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/23 15:40
 * @Version 1.0
 */
object DateUtil {
    /**
     * 年月日 时分秒
     */
    const val format_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss"

    /**
     * 年月日 时
     */
    const val format_yyyyMMddHH = "yyyy-MM-dd HH"

    /**
     * 年月日
     */
    const val format_yyyyMMdd = "yyyy-MM-dd"

    /**
     * 时分
     */
    const val format_HHmm = "HH:mm"

    /**
     * 日期转字符串
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDateToString(date: Date?, formatDate: String?): String? {
        return date?.let {
            val format: DateFormat = SimpleDateFormat(formatDate)
            format.format(date)
        }
    }
}