package com.mozhimen.basicktest.taskk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basicktest.databinding.ActivityTaskkExecutorBinding

/**
 * @ClassName ExecutorKActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/14 20:05
 * @Version 1.0
 */
class TaskKExecutorActivity : BaseActivityVB<ActivityTaskkExecutorBinding>() {

    private var _isPaused = false

    override fun initView(savedInstanceState: Bundle?) {
        vb.taskkExecutorBtnOrder.setOnClickListener {
            for (priority in 0..10) {
                TaskKExecutor.execute(TAG, priority) {
                    try {
                        Thread.sleep((1000 - priority * 100).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        vb.taskkExecutorBtnAllTask.setOnClickListener {
            if (_isPaused) {
                TaskKExecutor.resume()
            } else {
                TaskKExecutor.pause()
            }
            _isPaused = !_isPaused
        }

        vb.taskkExecutorBtnAsync.setOnClickListener {
            TaskKExecutor.execute(TAG, runnable = object : TaskKExecutor.ExecutorKCallable<String>() {
                override fun onBackground(): String {
                    Log.e(TAG, "onBackground: 当前线程: ${Thread.currentThread().name}")
                    return "我是异步任务的结果"
                }

                override fun onCompleted(t: String?) {
                    Log.e(TAG, "onCompleted: 当前线程是: ${Thread.currentThread().name}")
                    Log.e(TAG, "onCompleted: 任务结果是: $t")
                }
            })
        }
    }
}