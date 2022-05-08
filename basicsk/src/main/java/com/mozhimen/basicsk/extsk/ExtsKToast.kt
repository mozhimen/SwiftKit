package com.mozhimen.basicsk.utilk

import android.content.Context
import android.widget.Toast

/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 22:42
 * @Version 1.0
 */
/**
 * 作用: 简化吐司代码
 * 用法1: "...".showToast(context)
 * 用法2: R.string.app_name.showToast(context)
 * @receiver String
 * @param duration Int
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(UtilKGlobal.instance.getApp(), this, duration).show()
}

/**
 *
 * @receiver Int
 * @param duration Int
 */
fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(UtilKGlobal.instance.getApp(), this, duration).show()
}