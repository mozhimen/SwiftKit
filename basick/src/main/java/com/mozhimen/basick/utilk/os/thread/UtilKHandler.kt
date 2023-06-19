package com.mozhimen.basick.utilk.os.thread

import android.os.Handler
import android.os.Looper
import android.os.Message


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
    /**
     * postDelay
     * @param handler Handler
     * @param delayMills Long
     * @param runnable Runnable
     */
    @JvmStatic
    fun applyPostDelayed(handler: Handler, delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMills)
    }

    /**
     * 插到队首
     * @param handler Handler
     * @param runnable Runnable
     */
    @JvmStatic
    fun sendMsgAtFrontOfQueue(handler: Handler, runnable: Runnable) {
        val msg = Message.obtain(handler, runnable)
        handler.sendMessageAtFrontOfQueue(msg)
    }

    /**
     * 移除callbacks
     * @param handler Handler
     * @param runnable Runnable
     */
    @JvmStatic
    fun removeCbs(handler: Handler, runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    /**
     * 移除所有
     * @param handler Handler
     */
    @JvmStatic
    fun removeAllCbsAndMsgs(handler: Handler) {
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * 循环
     * @param block Function0<Unit>
     */
    @JvmStatic
    fun prepareAndLoop(block: () -> Unit) {
        var myLooper = Looper.myLooper()
        if (myLooper == null) {
            Looper.prepare()
            myLooper = Looper.myLooper()
        }
        block.invoke()
        if (myLooper != null) {
            Looper.loop()
            myLooper.quit()
        }
    }

    @JvmStatic
    fun postOnMain(block: () -> Unit) {
        Handler(Looper.getMainLooper()).post(block)
    }

    /**
     * 是否是MainLooper
     * @return Boolean
     */
    @JvmStatic
    fun isMainLooper(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}