package com.swiftmk.library.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.swiftmk.library.helper.ActivityBridger
import com.swiftmk.library.helper.ActivityBridgerImpl
import com.swiftmk.library.helper.ActivityCollector

/**
 * @ClassName StatusBarIniter1
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/9 17:15
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