package com.mozhimen.basick.elemk

import android.Manifest
import android.app.Activity
import android.os.Vibrator
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.taskk.commons.ITaskK
import com.mozhimen.basick.utilk.context.UtilKApplication

/**
 * @ClassName UtilKVibrate
 * @Description <uses-permission android:name="android.permission.VIBRATE" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 18:28
 * @Version 1.0
 */
@APermissionK(Manifest.permission.VIBRATE)
class ElemKVibrate(owner: LifecycleOwner) : ITaskK(owner) {

    private var _vibrator: Vibrator? = null

    /**
     * 震动
     * @param duration Long
     */
    fun start(duration: Long = 200L) {
        if (_vibrator == null) {
            _vibrator =
                UtilKApplication.instance.get()
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