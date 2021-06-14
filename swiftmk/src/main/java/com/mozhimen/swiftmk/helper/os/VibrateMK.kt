package com.mozhimen.swiftmk.helper.os

import android.app.Activity
import android.os.Vibrator

/**
 * @ClassName VibrateMK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/14 16:10
 * @Version 1.0
 */
/**
 * 作用: 系统震动
 * 用法: 需要权限 android.permission.VIBRATE
 */
class VibrateMK {
    fun vibrate(activity: Activity, duration: Int = 200) {
        val vibrator: Vibrator = activity.getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }
}