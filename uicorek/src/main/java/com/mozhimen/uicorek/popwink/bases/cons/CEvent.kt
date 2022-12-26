package com.mozhimen.uicorek.popwink.bases.cons

import android.os.Message


/**
 * @ClassName CEvent
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/26 12:43
 * @Version 1.0
 */
object CEvent {
    const val EVENT_SHOW = 1
    const val EVENT_DISMISS = 2
    const val EVENT_ALIGN_KEYBOARD = 3

    @JvmStatic
    fun getMessage(event: Int): Message {
        val msg = Message.obtain()
        msg.what = event
        return msg
    }
}