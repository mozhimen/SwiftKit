package com.swiftmk.library.helper

import android.content.Context
import android.content.Intent

interface ActivityBridger {

    /***
     * 用于跳转
     * 用法:
     * 静态继承(目标Activity):
     * companion object : ActivityBridger
     * 当前Activity:
     * val map = mapOf("param1" to "1", "param2" to "2")
     * SecondActivity.startContext(this, map as HashMap<String, String>)
     */
    fun startContext(context: Context, data: HashMap<String, String>) {
        val intent = Intent(context, this::class.java)
        for (params in data) {
            intent.putExtra(params.key, params.value)
        }
        context.startActivity(intent)
    }
}