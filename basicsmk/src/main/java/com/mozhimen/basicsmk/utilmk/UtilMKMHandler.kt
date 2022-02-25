package com.mozhimen.basicsmk.utilmk

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * @ClassName UtilMKMHandler
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:10
 * @Version 1.0
 */
class UtilMKMHandler {

    companion object {
        val instance = UtilMKMHandlerProvider.holder
    }

    private object UtilMKMHandlerProvider {
        val holder = UtilMKMHandler()
    }

    private val handler = Handler(Looper.getMainLooper())

    fun post(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelay(delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMills)
    }

    fun sendAtFrontOfQueue(runnable: Runnable) {
        val msg = Message.obtain(handler, runnable)
        handler.sendMessageAtFrontOfQueue(msg)
    }

    fun remove(runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }
}