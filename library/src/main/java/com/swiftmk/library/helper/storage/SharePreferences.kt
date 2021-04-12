package com.swiftmk.library.helper.storage

import android.content.SharedPreferences

/**
 * @ClassName SharePreferences
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/12 11:09
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
fun SharedPreferences.open(block:SharedPreferences.Editor.()->Unit){
    val editor=edit()
    editor.block()
    editor.apply()
}