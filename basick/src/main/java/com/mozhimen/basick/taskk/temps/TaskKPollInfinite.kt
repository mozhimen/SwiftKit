package com.mozhimen.basick.taskk.temps

import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.commons.ISuspend_Listener
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.basick.utilk.android.util.et
import kotlinx.coroutines.*

@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
class TaskKPollInfinite : BaseWakeBefDestroyTaskK() {
    private var _pollingScope: CoroutineScope? = null

    override fun isActive(): Boolean =
        _pollingScope != null && _pollingScope!!.isActive

    fun start(intervalMillis: Long, task: /*suspend*/ ISuspend_Listener) {
        if (isActive()) return
        val scope = CoroutineScope(Dispatchers.IO)
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