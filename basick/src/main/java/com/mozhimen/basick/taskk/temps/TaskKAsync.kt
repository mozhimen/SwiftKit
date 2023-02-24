package com.mozhimen.basick.taskk.temps

import com.mozhimen.basick.taskk.bases.BaseTaskK
import kotlinx.coroutines.*

typealias ITaskKAsyncErrorListener = (Throwable) -> Unit

class TaskKAsync : BaseTaskK() {
    private val _exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _taskKAsyncErrorListener?.invoke(throwable)        // 发生异常时的捕获
    }
    private var _taskKAsyncErrorListener: ITaskKAsyncErrorListener? = null
    private var _asyncScope: CoroutineScope = CoroutineScope(Dispatchers.IO + _exceptionHandler)

    fun isActive(): Boolean = _asyncScope.isActive

    fun setErrorListener(listener: ITaskKAsyncErrorListener) {
        this._taskKAsyncErrorListener = listener
    }

    fun execute(task: suspend () -> Unit) {
        if (isActive()) return
        _asyncScope.launch {
            task.invoke()
        }
    }

    override fun cancel() {
        if(!isActive()) return
        _asyncScope.cancel()
    }
}