package com.mozhimen.basick.executork

import kotlinx.coroutines.*
import java.lang.Runnable

class ExecutorKTaskPolling {
    private var _pollingScope: CoroutineScope? = null

    fun isRunning(): Boolean =
        _pollingScope != null && _pollingScope!!.isActive

    fun start(interval: Long, runnable: Runnable) {
        cancel()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            while (isActive) {
                try {
                    runnable.run()
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
}