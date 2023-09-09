package com.mozhimen.basicktest.elemk.android

import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.android.app.bases.BaseLifecycleService2
import com.mozhimen.basick.elemk.android.app.bases.BaseServiceBinder
import com.mozhimen.basick.elemk.android.app.commons.IBaseServiceConnListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName ElemKDemoService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:59
 * @Version 1.0
 */
class ElemKService : BaseLifecycleService2() {

    override var binder: IBaseServiceConnListener.Stub = object : BaseServiceBinder(serviceResListeners) {
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
            invoke("鸡你太美")
        }
    }
}
