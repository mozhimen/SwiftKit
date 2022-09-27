package com.mozhimen.basicktest.basick.basek

import com.mozhimen.basick.basek.service.BaseKService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName BaseKService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:59
 * @Version 1.0
 */
class BaseKDemoService : BaseKService() {

    private val _block = CoroutineScope(Dispatchers.IO).launch {
        //Thread.sleep(3000)
        delay(3000)
        onCallback("及你太美")
    }

    override fun onCreate() {
        super.onCreate()

        runBackgroundTasks()
    }

    private fun runBackgroundTasks() {
        _block.start()
    }

    override fun onDestroy() {
        _block.cancel()
        super.onDestroy()
    }
}
