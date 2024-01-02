package com.mozhimen.uicorektest.notifyk

import android.app.NotificationChannel
import android.content.Context
import android.view.View
import androidx.core.app.NotificationCompat
import com.mozhimen.basick.elemk.android.app.cons.CNotificationManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.app.UtilKNotificationManager
import com.mozhimen.basick.utilk.android.content.UtilKApplicationInfo
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.uicorektest.databinding.ActivityNotifykBinding

/**
 * @ClassName NotifyKActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/2 23:36
 * @Version 1.0
 */
class NotifyKActivity : BaseActivityVB<ActivityNotifykBinding>() {
    fun showNotification(view: View) {
        showNotification(this, NAME.hashCode(), "1", "2", "3", "4")
    }

    fun showNotification(
        context: Context,
        id: Int,
        channelName: CharSequence,
        contentTitle: CharSequence,
        contentText: CharSequence,
        subText: CharSequence
    ) {
        val notificationManager = UtilKNotificationManager.get(context)
        // 在 Android 8.0 及更高版本上，需要在系统中注册应用的通知渠道
        if (UtilKBuildVersion.isAfterV_26_8_O()) {
            val notificationChannel = NotificationChannel(NAME, channelName, CNotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, NAME)
            .setSmallIcon(UtilKApplicationInfo.getIcon(context))
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSubText(subText)
            .setOngoing(true)
            .setAutoCancel(true)
            .setProgress(100, 20, false)
        notificationManager.notify(id, builder.build())
    }
}