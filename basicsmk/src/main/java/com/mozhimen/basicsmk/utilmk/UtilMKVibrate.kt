package com.mozhimen.basicsmk.utilmk

import android.app.Activity
import android.os.Vibrator

/**
 * @ClassName UtilMKVibrate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 18:28
 * @Version 1.0
 */
class UtilMKVibrate {

    companion object {
        val instance = UtilMKVibrateProvider.holder
    }

    private object UtilMKVibrateProvider {
        val holder = UtilMKVibrate()
    }

    private var vibrator: Vibrator? = null

    fun start(duration: Int = 200) {
        if (vibrator == null) {
            vibrator =
                UtilMKGlobal.instance.getApp()!!
                    .getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator
        }
        vibrator!!.vibrate(duration)
    }

    fun stop() {
        vibrator?.cancel()
        vibrator = null
    }
}