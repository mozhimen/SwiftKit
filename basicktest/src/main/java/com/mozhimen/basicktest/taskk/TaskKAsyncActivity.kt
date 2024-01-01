package com.mozhimen.basicktest.taskk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.taskk.temps.TaskKAsync
import com.mozhimen.basicktest.databinding.ActivityTaskkAsyncBinding
import kotlinx.coroutines.delay

class TaskKAsyncActivity : BaseActivityVB<ActivityTaskkAsyncBinding>() {
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    private val _taskKAsync by lazy { TaskKAsync() }

    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
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