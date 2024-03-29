package com.mozhimen.basick.taskk.handler

import android.os.Handler
import android.os.Looper
import com.mozhimen.basick.utilk.android.os.UtilKLooper

/**
 * @ClassName TaskKHandler
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 15:51
 * @Version 1.0
 */
object TaskKHandler {
    private val _handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    @JvmStatic
    fun post(runnable: Runnable) {
        if (UtilKLooper.isMainLooper()) {
            runnable.run()
        } else {
            _handler.post(runnable)
        }
    }

    @JvmStatic
    fun postDelayed(delayMillis: Long, runnable: Runnable) {
        _handler.postDelayed(runnable, delayMillis)
    }
}