package com.mozhimen.basick.extsk

import android.widget.Toast
import com.mozhimen.basick.utilk.UtilKToast

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
    UtilKToast.showToast(this, duration)
}

/**
 * resId展示吐司
 * @receiver Int
 * @param duration Int
 */
fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToast(this, duration)
}

/**
 * 用法1: "...".showToastOnMain(context)
 * 用法2: R.string.app_name.showToastOnMain(context)
 * @receiver String
 * @param duration Int
 */
fun String.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(this, duration)
}

/**
 * resId展示吐司
 * @receiver Int
 * @param duration Int
 */
fun Int.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(this, duration)
}