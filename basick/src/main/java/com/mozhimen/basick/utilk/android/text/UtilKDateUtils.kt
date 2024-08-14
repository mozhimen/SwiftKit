package com.mozhimen.basick.utilk.android.text

import android.content.Context
import android.text.format.DateUtils

/**
 * @ClassName UtilKDateUtils
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/14
 * @Version 1.0
 */
object UtilKDateUtils {
    @JvmStatic
    fun formatDateTime(context: Context, millis: Long, flags: Int): String =
        DateUtils.formatDateTime(context, millis, flags)
}