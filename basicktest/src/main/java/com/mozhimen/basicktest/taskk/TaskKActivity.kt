package com.mozhimen.basicktest.taskk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.basicktest.databinding.ActivityTaskkBinding

class TaskKActivity : BaseActivityVB<ActivityTaskkBinding>() {
    fun goTaskKPoll(view: View) {
        start<TaskKPollActivity>()
    }

    fun goTaskKAsync(view: View) {
        start<TaskKAsyncActivity>()
    }

    fun goTaskKExecutor(view: View) {
        start<TaskKExecutorActivity>()
    }
}