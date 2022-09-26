package com.mozhimen.basicktest.loadk

import android.content.Context
import android.content.Intent
import com.mozhimen.basick.loadk.LoadKReceiverAutoRun
import kotlinx.coroutines.*

/**
 * @ClassName AutoRunReceiver
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 18:53
 * @Version 1.0
 */
class AutoRunReceiver() : LoadKReceiverAutoRun(LoadKActivity::class.java) {
    companion object {
        private val DELAY_TIME = 15//s
    }

    override fun onReceive(context: Context, intent: Intent) {
        runBlocking {
            delay(DELAY_TIME * 1000L)
            launch(context, intent)
        }
    }
}