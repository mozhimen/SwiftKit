package com.mozhimen.basicktest.executork

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.executork.ExecutorK
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityExecutorkBinding

/**
 * @ClassName ExecutorKActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/14 20:05
 * @Version 1.0
 */
class ExecutorKActivity : BaseKActivity<ActivityExecutorkBinding, BaseKViewModel>(R.layout.activity_executork) {

    private var _isPaused = false

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.executorkOrder.setOnClickListener {
            for (priority in 0..10) {
                ExecutorK.execute(TAG, priority) {
                    try {
                        Thread.sleep((1000 - priority * 100).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        vb.executorkAllTask.setOnClickListener {
            if (_isPaused) {
                ExecutorK.resume()
            } else {
                ExecutorK.pause()
            }
            _isPaused = !_isPaused
        }

        vb.executorkAsync.setOnClickListener {
            ExecutorK.execute(TAG, runnable = object : ExecutorK.ExecutorKCallable<String>() {
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