package com.mozhimen.basick.taskk

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.taskk.commons.ITaskK
import kotlinx.coroutines.*

class TaskKPolling(lifecycleOwner: LifecycleOwner) : ITaskK(lifecycleOwner) {
    private var _pollingScope: CoroutineScope? = null

    fun isRunning(): Boolean =
        _pollingScope != null && _pollingScope!!.isActive

    fun start(interval: Long, task: suspend () -> Unit) {
        cancel()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            while (isActive) {
                try {
                    task.invoke()
                } catch (e: Exception) {
                    if (e is CancellationException) return@launch
                    e.printStackTrace()
                }
                delay(interval)
            }
        }
        _pollingScope = scope
    }

    fun cancel() {
        _pollingScope?.cancel()
        _pollingScope = null
    }

    override fun onPause(owner: LifecycleOwner) {
        cancel()
        super.onPause(owner)
    }
}