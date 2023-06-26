package com.mozhimen.basick.taskk.temps

import com.mozhimen.basick.taskk.bases.BaseTaskK
import com.mozhimen.basick.utilk.android.util.et
import kotlinx.coroutines.*

class TaskKPoll : BaseTaskK() {
    private var _pollingScope: CoroutineScope? = null
    private var _time = 0

    override fun isActive(): Boolean =
        _pollingScope != null && _pollingScope!!.isActive

    /**
     *
     * @param interval Long 循环间隔时长
     * @param times Int 循环次数
     * @param task SuspendFunction1<Int, Unit>
     */
    fun start(interval: Long, times: Int, task: suspend (Int) -> Unit, onFinish: (() -> Unit)? = null) {
        if (isActive()) return
        _time = times
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            while (isActive && _time > 0) {
                try {
                    task.invoke(_time)
                } catch (e: Exception) {
                    if (e is CancellationException) return@launch
                    e.printStackTrace()
                    e.message?.et(TAG)
                }
                _time--
                delay(interval)
            }
            onFinish?.invoke()
            this@TaskKPoll.cancel()
        }
        _pollingScope = scope
    }

    override fun cancel() {
        if (!isActive()) return
        _pollingScope?.cancel()
        _pollingScope = null
        _time = 0
    }
}