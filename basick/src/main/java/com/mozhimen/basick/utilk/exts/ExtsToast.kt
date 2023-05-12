package com.mozhimen.basick.utilk.exts

import android.content.Context
import android.widget.Toast
import com.mozhimen.basick.utilk.view.bar.UtilKToast
import java.lang.Exception

/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 22:42
 * @Version 1.0
 */
/**
 * 用法1: "...".showToast(context)
 * 用法2: R.string.app_name.showToast(context)
 * @receiver String
 * @param duration Int
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.show(this, duration)
}

/**
 * resId展示吐司
 * @receiver Int
 * @param duration Int
 */
fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.show(this, duration)
}

/**
 * 长时土司
 * @receiver String
 */
fun String.showToastLong() {
    UtilKToast.show(this, Toast.LENGTH_LONG)
}

/**
 * 长时土司
 * @receiver Int
 */
fun Int.showToastLong() {
    UtilKToast.show(this, Toast.LENGTH_LONG)
}


/**
 * 用法1: "...".showToastOnMain(context)
 * 用法2: R.string.app_name.showToastOnMain(context)
 * @receiver String
 * @param duration Int
 */
fun String.showToastOnMain(duration: Int = Toast.LENGTH_LONG) {
    UtilKToast.showOnMain(this, duration)
}

/**
 * resId展示吐司
 * @receiver Int
 * @param duration Int
 */
fun Int.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showOnMain(this, duration)
}

fun Context.showToastOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showOnMain(msg, duration)
}

fun Context.showToastOnMain(id: Int, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showOnMain(getString(id), duration)
}

fun Exception.showToastOnMain(duration: Int = Toast.LENGTH_LONG) {
    this.message?.let { UtilKToast.showOnMain(it, duration) }
}