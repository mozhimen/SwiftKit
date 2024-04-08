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
fun Handler.postDelayed(delayMills: Long, runnable: Runnable) {
    UtilKHandler.postDelayed(this, delayMills, runnable)
}

fun Handler.sendMessageAtFrontOfQueue(runnable: Runnable) {
    UtilKHandler.sendMessageAtFrontOfQueue(this, runnable)
}

fun Handler.removeCallbacksAndMessages_ofNull() {
    UtilKHandler.removeCallbacksAndMessages_ofNull(this)
}

/////////////////////////////////////////////////////////////////

object UtilKHandler {
    @JvmStatic
    fun postDelayed(handler: Handler, delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMills)
    }

    //插到队首
    @JvmStatic
    fun sendMessageAtFrontOfQueue(handler: Handler, runnable: Runnable) {
        handler.sendMessageAtFrontOfQueue(Message.obtain(handler, runnable))
    }

    //移除callbacks
    @JvmStatic
    fun removeCallbacks(handler: Handler, runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    //移除所有
    @JvmStatic
    fun removeCallbacksAndMessages_ofNull(handler: Handler) {
        handler.removeCallbacksAndMessages(null)
    }

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun postOnMain(runnable: Runnable) {
        TaskKHandler.post(runnable)
    }

    @JvmStatic
    fun postDelayedOnMain(delayMillis: Long, runnable: Runnable) {
        TaskKHandler.postDelayed(delayMillis, runnable)
    }
}