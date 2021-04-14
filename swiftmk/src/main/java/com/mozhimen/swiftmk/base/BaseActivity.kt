package com.mozhimen.swiftmk.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.swiftmk.helper.activity.ActivityCollector

/**
 * @ClassName BaseActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:03
 * @Version 1.0
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * 打印日志
     */
    private val tag = "BaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, javaClass.simpleName)
        ActivityCollector.addActivity(this)
    }

    /**
     * 销毁Activity
     */
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}