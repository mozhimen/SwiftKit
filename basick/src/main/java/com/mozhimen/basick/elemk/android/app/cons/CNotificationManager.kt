package com.mozhimen.basick.elemk.android.app.cons

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CNotificationManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/2 22:38
 * @Version 1.0
 */
object CNotificationManager {
    @RequiresApi(CVersCode.V_24_7_N)
    const val IMPORTANCE_LOW = NotificationManager.IMPORTANCE_LOW
}