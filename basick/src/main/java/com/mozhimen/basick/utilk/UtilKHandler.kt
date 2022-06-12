package com.mozhimen.basick.utilk

import android.os.Handler
import android.os.Message

/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 10:31
 * @Version 1.0
 */
object UtilKHandler {
    /**
     * postDelay
     * @param handler Handler
     * @param delayMills Long
     * @param runnable Runnable
     */
    fun postDelayed(handler: Handler, delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMills)
    }

    /**
     * 插到队首
     * @param handler Handler
     * @param runnable Runnable
     */
    fun sendMsgAtFrontOfQueue(handler: Handler, runnable: Runnable) {
        val msg = Message.obtain(handler, runnable)
        handler.sendMessageAtFrontOfQueue(msg)
    }

    /**
     * 移除callbacks
     * @param handler Handler
     * @param runnable Runnable
     */
    fun removeCbs(handler: Handler, runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    /**
     * 移除所有
     * @param handler Handler
     */
    fun removeAll(handler: Handler) {
        handler.removeCallbacksAndMessages(null)
    }
}