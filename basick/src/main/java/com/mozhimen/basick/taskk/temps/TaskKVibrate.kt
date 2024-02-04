package com.mozhimen.basick.taskk.temps

import android.os.Vibrator
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.permission.OPermission_VIBRATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.basick.utilk.android.os.UtilKVibrator

/**
 * @ClassName UtilKVibrate
 * @Description <uses-permission android:name="android.permission.VIBRATE" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 18:28
 * @Version 1.0
 */
@OApiCall_BindLifecycle
@OApiInit_ByLazy
@OPermission_VIBRATE
class TaskKVibrate : BaseWakeBefDestroyTaskK() {

    private var _vibrator: Vibrator? = null

    /**
     * 震动
     * @param duration Long
     */
    @RequiresPermission(CPermission.VIBRATE)
    @OPermission_VIBRATE
    fun start(duration: Long = 200L) {
        if (isActive()) return
        if (_vibrator == null) {
            _vibrator = UtilKVibrator.get(_context)
        }
        _vibrator!!.vibrate(duration)
    }

    override fun isActive(): Boolean {
        return _vibrator != null
    }

    /**
     * 停止
     */
    @RequiresPermission(CPermission.VIBRATE)
    @OPermission_VIBRATE
    override fun cancel() {
        if (!isActive()) return
        _vibrator?.cancel()
        _vibrator = null
    }
}