package com.mozhimen.basicktest.basick.basek

import com.mozhimen.basick.basek.service.BaseKService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * @ClassName BaseKService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:59
 * @Version 1.0
 */
class BaseKDemoService : BaseKService() {

    override fun onCreate() {
        super.onCreate()

        runBackgroundTasks()
    }

    private fun runBackgroundTasks() {
        thread {
            Thread.sleep(3000)
            onCallback("鸡你太美")
        }
    }
}
