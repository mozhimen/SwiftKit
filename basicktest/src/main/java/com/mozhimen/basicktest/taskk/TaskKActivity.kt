package com.mozhimen.basicktest.taskk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.databinding.ActivityTaskkBinding

class TaskKActivity : BaseActivityVB<ActivityTaskkBinding>() {
    fun goTaskKPolling(view: View) {
        start<TaskKPollActivity>()
    }

    fun goTaskKAsync(view: View) {
        start<TaskKAsyncActivity>()
    }

    fun goTaskKThreadPool(view: View) {
        start<TaskKExecutorActivity>()
    }
}