package com.mozhimen.basicktest.taskk

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.taskk.temps.TaskKPoll
import com.mozhimen.basicktest.databinding.ActivityTaskkPollBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskKPollActivity : BaseActivityVB<ActivityTaskkPollBinding>() {
    private val _taskKPoll: TaskKPoll by lazy { TaskKPoll().apply { bindLifecycle(this@TaskKPollActivity) } }

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