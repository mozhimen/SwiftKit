package com.mozhimen.basick.taskk

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.taskk.commons.ITaskK
import kotlinx.coroutines.*

typealias ITaskKAsyncErrorListener = (Throwable) -> Unit

class TaskKAsync(lifecycleOwner: LifecycleOwner) : ITaskK(lifecycleOwner) {
    private val _exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _taskKAsyncErrorListener?.invoke(throwable)        // 发生异常时的捕获
    }
    private var _taskKAsyncErrorListener: ITaskKAsyncErrorListener? = null
    private var _asyncScope: CoroutineScope = CoroutineScope(Dispatchers.IO + _exceptionHandler)

    fun isRunning(): Boolean = _asyncScope.isActive

    fun setErrorListener(listener: ITaskKAsyncErrorListener) {
        this._taskKAsyncErrorListener = listener
    }

    fun execute(task: suspend () -> Unit) {
        _asyncScope.launch {
            task.invoke()
        }
    }

    override fun cancel() {
        _asyncScope.cancel()
    }
}