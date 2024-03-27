package com.mozhimen.basick.utilk.android.os

import android.os.Handler
import android.os.Message
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.taskk.handler.TaskKHandler


/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 10:31
 * @Version 1.0
 */
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
    fun postDelayed(handler: Handler, delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMills)
    }

    //插到队首
    @JvmStatic
    fun sendMsgAtFrontOfQueue(handler: Handler, runnable: Runnable) {
        handler.sendMessageAtFrontOfQueue(Message.obtain(handler, runnable))
    }

    //移除callbacks
    @JvmStatic
    fun removeCbs(handler: Handler, runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    //移除所有
    @JvmStatic
    fun removeAllCbsAndMsgs(handler: Handler) {
        handler.removeCallbacksAndMessages(null)
    }

    @JvmStatic
    fun postOnMain(block: I_Listener) {
        TaskKHandler.post(block)
//        Handler(UtilKLooper.getMainLooper()).post(block)
    }
}