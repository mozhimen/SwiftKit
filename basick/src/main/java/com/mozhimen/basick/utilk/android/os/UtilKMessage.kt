package com.mozhimen.basick.utilk.android.os

import android.os.Handler
import android.os.Message

/**
 * @ClassName UtilKMessage
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/27 19:47
 * @Version 1.0
 */
object UtilKMessage {
    @JvmStatic
    fun obtain(handler: Handler, runnable: Runnable): Message =
        Message.obtain(handler, runnable)
}