package com.mozhimen.basick.taskk.temps

import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.elemk.commons.ISuspend_Listener
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.basick.utilk.android.util.et
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@OApiCall_BindLifecycle
@OApiInit_ByLazy
open class TaskKPollInfinite : BaseWakeBefDestroyTaskK() {
    private var _pollingScope: CoroutineScope? = null

    override fun isActive(): Boolean =
        _pollingScope != null && _pollingScope!!.isActive

    /**
     *
     * @param intervalMillis Long 循环间隔时长
     * @param context CoroutineContext
     * @param task SuspendFunction0<Unit>
     */
    open fun start(intervalMillis: Long, context: CoroutineContext = Dispatchers.IO, task: /*suspend*/ ISuspend_Listener) {
        if (isActive()) return
        val scope = CoroutineScope(context)
        scope.launch {
            while (isActive) {
                try {
                    task.invoke()
                } catch (e: Exception) {
                    if (e is CancellationException) return@launch
                    e.printStackTrace()
                    e.message?.et(TAG)
                }
                delay(intervalMillis)
            }
        }
        _pollingScope = scope
    }

    override fun cancel() {
        if (!isActive()) return
        _pollingScope?.cancel()
        _pollingScope = null
    }
}