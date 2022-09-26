package com.mozhimen.basicktest.basick.basek

import com.mozhimen.basick.basek.BaseKService
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * @ClassName BaseKService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:59
 * @Version 1.0
 */
class BaseKDemoService : BaseKService<BaseKDemoService>() {

    inner class Binder : BaseKServiceBinder()

    private var _listener: (() -> Unit)? = null
    override fun onCreate() {
        super.onCreate()

        runBlocking {
            delay(1 * 1000)
            _listener?.invoke()
        }
    }

    fun setListener(listener: () -> Unit) {
        _listener = listener
    }
}