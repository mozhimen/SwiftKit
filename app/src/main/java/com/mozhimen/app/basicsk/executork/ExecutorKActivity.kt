package com.mozhimen.app.basicsk.executork

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityExecutorkBinding
import com.mozhimen.basicsk.executork.ExecutorK

/**
 * @ClassName ExecutorKActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/14 20:05
 * @Version 1.0
 */
class ExecutorKActivity : AppCompatActivity() {
    private val TAG = "ExecutorKActivity>>>>>"

    private var isPaused = false
    private val vb: ActivityExecutorkBinding by lazy {
        ActivityExecutorkBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
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
            if (isPaused) {
                ExecutorK.resume()
            } else {
                ExecutorK.pause()
            }
            isPaused = !isPaused
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