package com.mozhimen.componentk.netk.file.download

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.mozhimen.basick.elemk.android.app.cons.CDownloadManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.componentk.R
import com.mozhimen.componentk.netk.file.download.annors.ADownloadStatus
import java.io.File


/**
 *
 * @author by chiclaim@google.com
 */
internal class NotifierUtils private constructor() {

    companion object {
        private fun getPendingIntentFlag() =
            if (UtilKBuildVersion.isAfterV_23_6_M()) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else PendingIntent.FLAG_UPDATE_CURRENT


        private fun getNotificationManager(context: Context): NotificationManager {
            return context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
        }

        @SuppressLint("SwitchIntDef")
        fun showNotification(
            context: Context,
            id: Int,
            @DrawableRes notifierSmallIcon: Int,
            percent: Int,
            title: CharSequence,
            content: CharSequence?,
            @ADownloadStatus status: Int,
            file: File? = null,
            url: String? = null
        ) {
            val notificationManager = getNotificationManager(context)
            // 在 Android 8.0 及更高版本上，需要在系统中注册应用的通知渠道
            if (UtilKBuildVersion.isAfterV_26_8_O()) {
                val channel = NotificationChannel(
                    CDownloadParameter.NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.netk_file_notifier_channel_name),
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationManager.createNotificationChannel(channel)
            }

            val builder = NotificationCompat.Builder(context, CDownloadParameter.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(notifierSmallIcon)
                .setContentTitle(title)
                .setAutoCancel(status == CDownloadManager.STATUS_SUCCESSFUL) // canceled when it is clicked by the user.
                .setOngoing(percent != 100)

            if (percent >= 0) {
                builder.setSubText( // don't use setContentInfo(deprecated in API level 24)
                    context.getString(
                        R.string.netk_file_notifier_subtext_placeholder,
                        percent
                    )
                )
            }
            when (status) {
                CDownloadManager.STATUS_SUCCESSFUL -> {
                    // click to install
                    file?.let {
//                        val clickIntent = createInstallIntent(context, it)
//                        val pendingIntent = PendingIntent.getActivity(
//                            context,
//                            0,
//                            clickIntent,
//                            getPendingIntentFlag()
//                        )
//                        builder.setContentIntent(pendingIntent)
                    }
                }

                CDownloadManager.STATUS_RUNNING -> {
                    builder.setProgress(100, percent, percent <= 0)
                }

                CDownloadManager.STATUS_FAILED -> {
                    val intent =
                        Intent("${context.packageName}.DownloadService")
                    intent.setPackage(context.packageName)
                    intent.putExtra(CDownloadParameter.EXTRA_URL, url)
                    intent.putExtra(CDownloadParameter.EXTRA_FROM, CDownloadParameter.EXTRA_FROM_NOTIFIER)
                    val pendingIntent =
                        PendingIntent.getService(context, 1, intent, getPendingIntentFlag())
                    builder.setContentIntent(pendingIntent)
                    //builder.addAction(NotificationCompat.Action(null, null, pendingIntent))
                }
            }

            content?.let {
                builder.setContentText(it)
            }
            notificationManager.notify(id, builder.build())
        }

        fun cancelNotification(context: Context, id: Int) {
            getNotificationManager(context).cancel(id)
        }

    }


}