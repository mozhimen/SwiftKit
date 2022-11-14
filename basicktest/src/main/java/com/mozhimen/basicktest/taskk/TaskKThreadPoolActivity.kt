package com.mozhimen.basicktest.taskk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.taskk.threadpool.TaskKThreadPool
import com.mozhimen.basicktest.databinding.ActivityTaskkThreadPoolBinding

/**
 * @ClassName ExecutorKActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/14 20:05
 * @Version 1.0
 */
class TaskKThreadPoolActivity : BaseKActivityVB<ActivityTaskkThreadPoolBinding>() {

    private var _isPaused = false

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.taskkThreadPoolBtnOrder.setOnClickListener {
            for (priority in 0..10) {
                TaskKThreadPool.execute(TAG, priority) {
                    try {
                        Thread.sleep((1000 - priority * 100).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        vb.taskkThreadPoolBtnAllTask.setOnClickListener {
            if (_isPaused) {
                TaskKThreadPool.resume()
            } else {
                TaskKThreadPool.pause()
            }
            _isPaused = !_isPaused
        }

        vb.taskkThreadPoolBtnAsync.setOnClickListener {
            TaskKThreadPool.execute(TAG, runnable = object : TaskKThreadPool.ExecutorKCallable<String>() {
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