package com.mozhimen.basick.elemk

import android.app.Activity
import android.os.Vibrator
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.taskk.bases.BaseTaskK
import com.mozhimen.basick.utilk.content.UtilKApplication

/**
 * @ClassName UtilKVibrate
 * @Description <uses-permission android:name="android.permission.VIBRATE" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 18:28
 * @Version 1.0
 */
@AManifestKRequire(CPermission.VIBRATE)
class ElemKVibrate() : BaseTaskK() {

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