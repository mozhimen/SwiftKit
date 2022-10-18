package com.mozhimen.basicktest.prefabk

import android.content.Context
import android.content.Intent
import com.mozhimen.basick.prefabk.receiver.PrefabKReceiverAutoRun
import com.mozhimen.basicktest.BasicKActivity
import kotlinx.coroutines.*

/**
 * @ClassName AutoRunReceiver
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 18:53
 * @Version 1.0
 */
class AutoRunReceiver() : PrefabKReceiverAutoRun(BasicKActivity::class.java) {
    companion object {
        private val DELAY_TIME = 10//s
    }

    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(DELAY_TIME * 1000L)
            launch(context, intent)
        }
    }
}