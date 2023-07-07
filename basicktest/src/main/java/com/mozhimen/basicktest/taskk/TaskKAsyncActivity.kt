package com.mozhimen.basicktest.taskk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.taskk.temps.TaskKAsync
import com.mozhimen.basicktest.databinding.ActivityTaskkAsyncBinding
import kotlinx.coroutines.delay

class TaskKAsyncActivity : BaseActivityVB<ActivityTaskkAsyncBinding>() {
    @OptIn(AOptLazyInit::class)
    private val _taskKAsync by lazy { TaskKAsync() }

    @OptIn(AOptLazyInit::class)
    override fun initView(savedInstanceState: Bundle?) {
        _taskKAsync.bindLifecycle(this)
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