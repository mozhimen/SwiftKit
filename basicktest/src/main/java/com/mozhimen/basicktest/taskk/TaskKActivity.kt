package com.mozhimen.basicktest.taskk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext
import com.mozhimen.basicktest.databinding.ActivityTaskkBinding

class TaskKActivity : BaseActivityVB<ActivityTaskkBinding>() {
    fun goTaskKPoll(view: View) {
        startContext<TaskKPollActivity>()
    }

    fun goTaskKAsync(view: View) {
        startContext<TaskKAsyncActivity>()
    }

    fun goTaskKExecutor(view: View) {
        startContext<TaskKExecutorActivity>()
    }
}