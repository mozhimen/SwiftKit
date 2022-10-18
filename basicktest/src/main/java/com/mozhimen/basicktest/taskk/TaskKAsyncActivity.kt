package com.mozhimen.basicktest.taskk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.taskk.TaskKAsync
import com.mozhimen.basick.utilk.UtilKDate
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityTaskkAsyncBinding
import kotlinx.coroutines.delay

class TaskKAsyncActivity : BaseKActivity<ActivityTaskkAsyncBinding, BaseKViewModel>(R.layout.activity_taskk_async) {
    private val _taskKAsync = TaskKAsync(this)
    override fun initData(savedInstanceState: Bundle?) {
        vb.taskkAsyncBtn.setOnClickListener {
            for (i in 0 until 10) {
                _taskKAsync.execute {
                    val time = System.currentTimeMillis()
                    delay((1000..3000).random().toLong())
                    Log.d(TAG, "initData: 线程${i}执行结束, 执行时间:${System.currentTimeMillis() - time}")
                }
            }
        }
    }
}