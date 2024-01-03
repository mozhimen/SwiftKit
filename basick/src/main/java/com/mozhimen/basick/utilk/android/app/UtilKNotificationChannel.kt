package com.mozhimen.basick.utilk.android.app

import android.app.NotificationChannel
import android.os.Build
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKNotificationChannel
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/3
 * @Version 1.0
 */
object UtilKNotificationChannel {
    @RequiresApi(CVersCode.V_26_8_O)
    fun get(id: String, channelName: String, importance: Int): NotificationChannel =
        NotificationChannel(id, channelName, importance)
}