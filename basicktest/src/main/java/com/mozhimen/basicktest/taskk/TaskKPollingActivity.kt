package com.mozhimen.basicktest.taskk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.taskk.temps.TaskKPoll
import com.mozhimen.basicktest.databinding.ActivityTaskkPollingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskKPollingActivity : BaseKActivityVB<ActivityTaskkPollingBinding>() {
    private var _taskKPoll: TaskKPoll = TaskKPoll(this)
    override fun initData(savedInstanceState: Bundle?) {
        vb.eventkTaskPollingStart.setOnClickListener {
            _taskKPoll.start(5000) {
                withContext(Dispatchers.Main){
                    System.currentTimeMillis().toString().showToast()
                }
            }
        }
        vb.eventkTaskPollingCancel.setOnClickListener {
            _taskKPoll.cancel()
        }
    }
}