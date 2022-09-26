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
open class BaseKService<T> : Service() {
    override fun onBind(intent: Intent?): IBinder {
        return BaseKServiceBinder()
    }

    open inner class BaseKServiceBinder : Binder() {
        val service: T
            get() = this as T
    }
}