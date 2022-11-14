package com.mozhimen.basicktest.taskk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.databinding.ActivityTaskkBinding

class TaskKActivity : BaseKActivityVB<ActivityTaskkBinding>() {
    fun goTaskKPolling(view: View) {
        start<TaskKPollingActivity>()
    }

    fun goTaskKAsync(view: View) {
        start<TaskKAsyncActivity>()
    }

    fun goTaskKThreadPool(view: View) {
        start<TaskKThreadPoolActivity>()
    }


    override fun initData(savedInstanceState: Bundle?) {

    }
}