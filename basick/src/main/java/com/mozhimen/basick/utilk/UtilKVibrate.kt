package com.mozhimen.basick.utilk

import android.app.Activity
import android.os.Vibrator

/**
 * @ClassName UtilKVibrate
 * @Description <uses-permission android:name="android.permission.VIBRATE" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 18:28
 * @Version 1.0
 */
class UtilKVibrate {

    companion object {
        val instance = UtilKVibrateProvider.holder
    }

    private object UtilKVibrateProvider {
        val holder = UtilKVibrate()
    }

    private var vibrator: Vibrator? = null

    /**
     * 震动
     * @param duration Long
     */
    fun start(duration: Long = 200L) {
        if (vibrator == null) {
            vibrator =
                UtilKGlobal.instance.getApp()!!
                    .getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator
        }
        vibrator!!.vibrate(duration)
    }

    /**
     * 停止
     */
    fun stop() {
        vibrator?.cancel()
        vibrator = null
    }
}