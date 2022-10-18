package com.mozhimen.basicktest.taskk

import android.view.View
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityTaskkBinding

class TaskKActivity : BaseKActivity<ActivityTaskkBinding, BaseKViewModel>(R.layout.activity_taskk) {
    fun goTaskKPolling(view: View) {
        start<TaskKPollingActivity>()
    }

    fun goTaskKAsync(view: View) {
        start<TaskKAsyncActivity>()
    }
}