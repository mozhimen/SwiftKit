package com.mozhimen.basick.utilk.exts

import android.os.Handler
import com.mozhimen.basick.utilk.os.thread.UtilKHandler

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
    UtilKHandler.postDelayed(this, delayMills, runnable)
}

/**
 * 插到队首
 * @receiver Handler
 * @param runnable Runnable
 */
fun Handler.sendMsgAtFrontOfQueue(runnable: Runnable) {
    UtilKHandler.sendMsgAtFrontOfQueue(this, runnable)
}

/**
 * 移除
 * @receiver Handler
 * @param runnable Runnable
 */
fun Handler.removeCbs(runnable: Runnable) {
    UtilKHandler.removeCbs(this, runnable)
}

/**
 * 移除
 * @receiver Handler
 */
fun Handler.removeAllCbsAndMsgs() {
    UtilKHandler.removeAllCbsAndMsgs(this)
}