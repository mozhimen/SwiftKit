package com.mozhimen.app.basicsmk.executormk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityExecutormkBinding
import com.mozhimen.basicsmk.executormk.ExecutorMK
import com.mozhimen.basicsmk.executormk.ExecutorMK.ExecutorMKCallback

/**
 * @ClassName ExecutorMKActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/14 20:05
 * @Version 1.0
 */
class ExecutorMKActivity : AppCompatActivity() {
    private val TAG = "ExecutorMKActivity>>>>>"
    
    private var isPaused = false
    private val vb: ActivityExecutormkBinding by lazy {
        ActivityExecutormkBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        vb.executormkOrder.setOnClickListener {
            for (priority in 0..10) {
                ExecutorMK.execute(Runnable {
                    try {
                        Thread.sleep((1000 - priority * 100).toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }, priority)
            }
        }

        vb.executormkAllTask.setOnClickListener {
            if (isPaused) {
                ExecutorMK.resume()
            } else {
                ExecutorMK.pause()
            }
            isPaused = !isPaused
        }

        vb.executormkAsync.setOnClickListener {
            ExecutorMK.execute(object : ExecutorMKCallback<String>() {
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