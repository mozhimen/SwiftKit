package com.mozhimen.basick.utilk.android.app

import android.app.NotificationManager
import android.content.Context
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKNotificationManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/2 21:33
 * @Version 1.0
 */
object UtilKNotificationManager {

    @JvmStatic
    fun get(context: Context): NotificationManager =
        UtilKContext.getNotificationManager(context)
}