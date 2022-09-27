package com.mozhimen.basick.basek

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * @ClassName BaseKService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:36
 * @Version 1.0
 */
abstract class BaseKService : Service() {
    abstract val binder: Binder
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}