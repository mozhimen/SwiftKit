package com.mozhimen.basicktest.elemk.service

import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.service.commons.BaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName BaseKService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:59
 * @Version 1.0
 */
class ElemKDemoService : BaseService() {

    override fun onCreate() {
        super.onCreate()

        runBackgroundTasks()
    }

    private var _job: Job? = null

    private fun runBackgroundTasks() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(3000)
            onCallback("鸡你太美")
        }
    }
}
