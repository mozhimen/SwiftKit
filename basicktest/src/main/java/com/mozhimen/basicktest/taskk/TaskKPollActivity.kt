package com.mozhimen.basicktest.taskk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.taskk.temps.TaskKPoll
import com.mozhimen.basicktest.databinding.ActivityTaskkPollBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskKPollActivity : BaseActivityVDB<ActivityTaskkPollBinding>() {
    @OptIn(OApiCall_BindLifecycle::class, OApiInit_ByLazy::class)
    private val _taskKPoll: TaskKPoll by lazy_ofNone { TaskKPoll().apply { bindLifecycle(this@TaskKPollActivity) } }

    @OptIn(OApiCall_BindLifecycle::class, OApiInit_ByLazy::class)
    override fun initView(savedInstanceState: Bundle?) {
        vdb.eventkTaskPollBtnStart.setOnClickListener {
            _taskKPoll.start(1000, 3, {
                withContext(Dispatchers.Main) {
                    it.toString().showToast()
                }
            })
        }
        vdb.eventkTaskPollBtnCancel.setOnClickListener {
            _taskKPoll.cancel()
        }
    }
}