package com.mozhimen.basick.utilk.android.os

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.mozhimen.basick.elemk.commons.I_Listener


/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 10:31
 * @Version 1.0
 */
fun Handler.applyPostDelayed(delayMills: Long, runnable: Runnable) {
    UtilKHandler.applyPostDelayed(this, delayMills, runnable)
}

fun Handler.sendMsgAtFrontOfQueue(runnable: Runnable) {
    UtilKHandler.sendMsgAtFrontOfQueue(this, runnable)
}

fun Handler.removeCbs(runnable: Runnable) {
    UtilKHandler.removeCbs(this, runnable)
}

fun Handler.removeAllCbsAndMsgs() {
    UtilKHandler.removeAllCbsAndMsgs(this)
}

object UtilKHandler {
    @JvmStatic
    fun applyPostDelayed(handler: Handler, delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMills)
    }

    /**
     * 插到队首
     */
    @JvmStatic
    fun sendMsgAtFrontOfQueue(handler: Handler, runnable: Runnable) {
        handler.sendMessageAtFrontOfQueue(Message.obtain(handler, runnable))
    }

    /**
     * 移除callbacks
     */
    @JvmStatic
    fun removeCbs(handler: Handler, runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    /**
     * 移除所有
     */
    @JvmStatic
    fun removeAllCbsAndMsgs(handler: Handler) {
        handler.removeCallbacksAndMessages(null)
    }

    @JvmStatic
    fun postOnMain(block: I_Listener) {
        Handler(UtilKLooper.getMainLooper()).post(block)
    }
}