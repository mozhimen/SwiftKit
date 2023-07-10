package com.mozhimen.basick.taskk.temps

import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.elemk.commons.ISuspA_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basick.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.basick.utilk.android.util.et
import kotlinx.coroutines.*

@AOptInNeedCallBindLifecycle
@AOptInInitByLazy
class TaskKPoll : BaseWakeBefDestroyTaskK() {
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
    fun start(interval: Long, times: Int, task: ISuspA_Listener<Int>/*suspend (Int) -> Unit*/, onFinish: I_Listener? = null) {
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