package com.mozhimen.swiftmk.helper.toast

import android.content.Context
import android.widget.Toast

/**
 * @ClassName Toast
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:11
 * @Version 1.0
 */

/**
 * 作用: 简化吐司代码
 * 用法1: "...".showToast(context)
 * 用法2: R.string.app_name.showToast(context)
 */
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}