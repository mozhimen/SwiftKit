package com.mozhimen.basicktest.taskk

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityTaskkBinding

class TaskKActivity : BaseActivityVB<ActivityTaskkBinding>() {
    fun goTaskKAsync(view: View) {
        startContext<TaskKAsyncActivity>()
    }

    fun goTaskKCountDown(view: View) {
        startContext<TaskKCountDownActivity>()
    }

    fun goTaskKExecutor(view: View) {
        startContext<TaskKExecutorActivity>()
    }

    fun goTaskKPoll(view: View) {
        startContext<TaskKPollActivity>()
    }
}