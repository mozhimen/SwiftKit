package com.swiftmk.library.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.swiftmk.library.helper.ActivityCollector

object BaseActivity : AppCompatActivity() {

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

    fun startContext(context: Context, data: HashMap<String, String>) {
        val intent = Intent(context, this::class.java)
        for (params in data) {
            intent.putExtra(params.key, params.value)
        }
        context.startActivity(intent)
    }
}