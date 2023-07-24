package com.mozhimen.basick.elemk.java.util

import android.os.Handler
import java.util.concurrent.Executor


/**
 * @ClassName BaseHandlerExecutor
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/24 18:56
 * @Version 1.0
 */
class BaseHandlerExecutor(private val _handler: Handler) : Executor {
    override fun execute(command: Runnable) {
        _handler.post(command)
    }
}