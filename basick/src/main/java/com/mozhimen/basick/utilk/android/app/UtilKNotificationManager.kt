package com.mozhimen.basick.utilk.android.app

import android.app.NotificationManager
import android.content.Context
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKNotificationManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/2 21:33
 * @Version 1.0
 */
object UtilKNotificationManager {
    @JvmStatic
    fun createNotificationChannel(notificationManager: NotificationManager, channelId: String, channelName: String, importance: Int) {
        if (UtilKBuildVersion.isAfterV_26_8_O()) {// 在 Android 8.0 及更高版本上，需要在系统中注册应用的通知渠道
            notificationManager.createNotificationChannel(UtilKNotificationChannel.get(channelId, channelName, importance))
        }
    }

    @JvmStatic
    fun get(context: Context): NotificationManager =
        UtilKContext.getNotificationManager(context)
}