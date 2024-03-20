package com.mozhimen.basick.utilk.androidx.core

import android.app.Activity
import androidx.annotation.IntRange
import androidx.core.app.ActivityCompat

/**
 * @ClassName UtilKActivityCompat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/20
 * @Version 1.0
 */
object UtilKActivityCompat {

    @JvmStatic
    fun requestPermissions(activity: Activity, permissions: Array<String>, @IntRange(from = 0) requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }
}