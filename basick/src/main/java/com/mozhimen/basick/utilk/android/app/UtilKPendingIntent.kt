package com.mozhimen.basick.utilk.android.app

import com.mozhimen.basick.elemk.android.app.cons.CPendingIntent
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKPendingIntent
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/2 21:48
 * @Version 1.0
 */
object UtilKPendingIntent {
    @JvmStatic
    fun getFlagOfUpdate(): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            CPendingIntent.FLAG_UPDATE_CURRENT or CPendingIntent.FLAG_IMMUTABLE
        } else CPendingIntent.FLAG_UPDATE_CURRENT
}