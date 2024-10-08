package com.mozhimen.basicktest.taskk

import android.view.View
import com.mozhimen.bindk.bases.viewdatabinding.activity.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityTaskkBinding

class TaskKActivity : BaseActivityVDB<ActivityTaskkBinding>() {
    fun goTaskKAsync(view: View) {
        startContext<TaskKAsyncActivity>()
    }

    fun goTaskKCountDown(view: View) {
        startContext<TaskKCountDownActivity>()
    }

    fun goTaskKLocation(view: View) {
        startContext<TaskKLocationActivity>()
    }

    fun goTaskKPoll(view: View) {
        startContext<TaskKPollActivity>()
    }
}