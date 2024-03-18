package com.mozhimen.basick.taskk.temps

import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.elemk.commons.ISuspend_Listener
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.basick.utilk.android.util.e
import kotlinx.coroutines.*

typealias ITaskKAsyncErrorListener = IA_Listener<Throwable>//(Throwable) -> Unit

@OApiCall_BindLifecycle
@OApiInit_ByLazy
class TaskKAsync : BaseWakeBefDestroyTaskK() {
    private val _exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        throwable.message?.e(TAG)
        _taskKAsyncErrorListener?.invoke(throwable)        // 发生异常时的捕获
    }
    private var _taskKAsyncErrorListener: ITaskKAsyncErrorListener? = null
    private var _asyncScope: CoroutineScope = CoroutineScope(Dispatchers.IO + _exceptionHandler)

    override fun isActive(): Boolean = _asyncScope.isActive

    fun setErrorListener(listener: ITaskKAsyncErrorListener) {
        this._taskKAsyncErrorListener = listener
    }

    fun execute(task: ISuspend_Listener) {
        if (isActive()) return
        _asyncScope.launch {
            task.invoke()
        }
    }

    override fun cancel() {
        if (!isActive()) return
        _asyncScope.cancel()
    }
}