package com.mozhimen.basicktest.taskk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.taskk.TaskKPolling
import com.mozhimen.basicktest.databinding.ActivityTaskkPollingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskKPollingActivity : BaseKActivityVB<ActivityTaskkPollingBinding>() {
    private var _taskKPolling: TaskKPolling = TaskKPolling(this)
    override fun initData(savedInstanceState: Bundle?) {
        vb.eventkTaskPollingStart.setOnClickListener {
            _taskKPolling.start(5000) {
                withContext(Dispatchers.Main){
                    System.currentTimeMillis().toString().showToast()
                }
            }
        }
        vb.eventkTaskPollingCancel.setOnClickListener {
            _taskKPolling.cancel()
        }
    }
}