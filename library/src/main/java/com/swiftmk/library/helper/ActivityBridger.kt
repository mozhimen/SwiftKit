package com.swiftmk.library.helper

import android.content.Context
import android.content.Intent

/**
 * @ClassName StatusBarIniter1
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/9 17:15
 * @Version 1.0
 */

/***
 * 作用: 用于跳转
 * 用法:
 * 1.静态继承(目标Activity):
 * companion object : ActivityBridgerImpl {
 *  override fun actionStart(context: Context, data: HashMap<String, String>) {
 *      ActivityBridger.actionStart(context, SecondActivity::class.java, data)}}
 *
 * 2.当前Activity:
 * val map = mapOf("param1" to "1", "param2" to "2")
 * SecondActivity.actionStart(this, map as HashMap<String, String>)
 */
interface ActivityBridgerImpl {
    fun actionStart(context: Context, data: HashMap<String, String>)
}

object ActivityBridger {

    fun actionStart(context: Context, cls: Class<*>, data: HashMap<String, String>) {
        val intent = Intent(context, cls).apply {
            for (param in data) {
                putExtra(param.key, param.value)
            }
        }
        context.startActivity(intent)
    }
}