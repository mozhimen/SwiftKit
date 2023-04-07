package com.mozhimen.basicktest.elemk.service

import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.service.bases.BaseService
import com.mozhimen.basick.elemk.service.commons.IBaseServiceConnListener
import com.mozhimen.basick.elemk.service.commons.IBaseServiceResListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName ElemKDemoService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:59
 * @Version 1.0
 */
class ElemKService : BaseService() {

    override var BINDER: IBaseServiceConnListener.Stub = object : BaseServiceBinder() {
        override fun launchCommand(cmd: String?): String {
            return if (cmd == "123") "456" else "123"
        }
    }

    override fun onCreate() {
        super.onCreate()

        runBackgroundTasks()
    }

    private fun runBackgroundTasks() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(3000)
            onCallback("鸡你太美")
        }
    }
}
