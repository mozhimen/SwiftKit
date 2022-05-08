package com.mozhimen.basicsk.extsk

import android.os.Handler
import android.os.Message

/**
 * @ClassName ExtsKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 22:24
 * @Version 1.0
 */
/**
 * postDelay
 * @receiver Handler
 * @param delayMills Long
 * @param runnable Runnable
 */
fun Handler.postDelayed(delayMills: Long, runnable: Runnable) {
    this.postDelayed(runnable, delayMills)
}

/**
 * 插到队首
 * @receiver Handler
 * @param runnable Runnable
 */
fun Handler.sendAtFrontOfQueue(runnable: Runnable) {
    val msg = Message.obtain(this, runnable)
    this.sendMessageAtFrontOfQueue(msg)
}

/**
 * 移除
 * @receiver Handler
 * @param runnable Runnable
 */
fun Handler.remove(runnable: Runnable) {
    this.removeCallbacks(runnable)
}