package com.mozhimen.swiftmk.helper.storage

import android.content.SharedPreferences

/**
 * @ClassName SharedPreferences
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:10
 * @Version 1.0
 */

/**
 * 作用: 简化SharePreferences写法
 * 用法:
 * getSharedPreferences("data", Context.MODE_PRIVATE).open {
 *  putString("name","Tom")
 *  ...
 * }
 */
fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.block()
    editor.apply()
}