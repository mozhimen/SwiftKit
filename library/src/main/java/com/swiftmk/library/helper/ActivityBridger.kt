package com.swiftmk.library.helper

import android.content.Context
import android.content.Intent

interface ActivityBridgerImpl {
    fun startContext(context: Context, data: HashMap<String, String>)
}

object ActivityBridger {
    /***
     * 用于跳转
     * 用法:
     * 静态继承(目标Activity):
     * companion object : ActivityBridger
     * 当前Activity:
     * val map = mapOf("param1" to "1", "param2" to "2")
     * SecondActivity.startContext(this, map as HashMap<String, String>)
     * 实现
     */
    fun startContext(context: Context, cls: Class<*>, data: HashMap<String, String>) {
        val intent = Intent(context, cls).apply {
            for (param in data) {
                putExtra(param.key, param.value)
            }
        }
        context.startActivity(intent)
    }
}