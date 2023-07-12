package com.mozhimen.basicktest.taskk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.taskk.temps.TaskKPoll
import com.mozhimen.basicktest.databinding.ActivityTaskkPollBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskKPollActivity : BaseActivityVB<ActivityTaskkPollBinding>() {
    @OptIn(ALintKOptIn_ApiCall_BindLifecycle::class, ALintKOptIn_ApiInit_ByLazy::class)
    private val _taskKPoll: TaskKPoll by lazy { TaskKPoll().apply { bindLifecycle(this@TaskKPollActivity) } }

    @OptIn(ALintKOptIn_ApiCall_BindLifecycle::class, ALintKOptIn_ApiInit_ByLazy::class)
    override fun initView(savedInstanceState: Bundle?) {
        vb.eventkTaskPollBtnStart.setOnClickListener {
            _taskKPoll.start(1000, 3, {
                withContext(Dispatchers.Main) {
                    it.toString().showToast()
                }
            })
        }
        vb.eventkTaskPollBtnCancel.setOnClickListener {
            _taskKPoll.cancel()
        }
    }
}