package com.mozhimen.basick.prefabk

import android.app.Activity
import android.os.Vibrator
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.taskk.coroutine.commons.ITaskK
import com.mozhimen.basick.utilk.UtilKGlobal

/**
 * @ClassName UtilKVibrate
 * @Description <uses-permission android:name="android.permission.VIBRATE" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 18:28
 * @Version 1.0
 */
class PrefabKVibrate(owner: LifecycleOwner) : ITaskK(owner) {

    private var _vibrator: Vibrator? = null

    /**
     * 震动
     * @param duration Long
     */
    fun start(duration: Long = 200L) {
        if (_vibrator == null) {
            _vibrator =
                UtilKGlobal.instance.getApp()!!
                    .getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator
        }
        _vibrator!!.vibrate(duration)
    }

    /**
     * 停止
     */
    override fun cancel() {
        _vibrator?.cancel()
        _vibrator = null
    }
}